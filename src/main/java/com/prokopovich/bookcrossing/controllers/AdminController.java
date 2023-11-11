package com.prokopovich.bookcrossing.controllers;

import com.prokopovich.bookcrossing.dto.UserDtoToShow;
import com.prokopovich.bookcrossing.entities.City;
import com.prokopovich.bookcrossing.entities.Location;
import com.prokopovich.bookcrossing.entities.Role;
import com.prokopovich.bookcrossing.exceptions.AdminException;
import com.prokopovich.bookcrossing.exceptions.DuplicateCityException;
import com.prokopovich.bookcrossing.exceptions.DuplicateLocationException;
import com.prokopovich.bookcrossing.exceptions.HostException;
import com.prokopovich.bookcrossing.services.CityService;
import com.prokopovich.bookcrossing.services.LocationService;
import com.prokopovich.bookcrossing.services.UserService;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin/actions")
public class AdminController {
    private CityService cityService;
    private LocationService locationService;
    private UserService userService;

    @Autowired
    public AdminController(CityService cityService, LocationService locationService, UserService userService) {
        this.cityService = cityService;
        this.locationService = locationService;
        this.userService = userService;
    }

    @GetMapping("")
    public String showActionsPage() {
        return "settings/full-functional";
    }
    @GetMapping("/q")//test end-point
    public String showActionsPageq() {
        return "q";
    }

    @GetMapping("/cities")
    public String showCities(Model model) {
        List<City> cityList = cityService.findAllCities();
        model.addAttribute("list", cityList);
        return "settings/cities";
    }

    @PostMapping("/cities/newcity")
    public String addCity(@RequestParam("name") String name, Model model) {
        try {
            City city = new City(name);
            cityService.addCity(city);
            return "redirect:/admin/actions/cities";
        } catch (DuplicateCityException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "settings/error";
        }
    }

    @PostMapping("/cities/update")
    public String updateCity(@RequestParam("newname") String newName, @RequestParam("id") Integer id) {
        cityService.modifyCity(newName, id);
        return "redirect:/admin/actions/cities";
    }


    @GetMapping("/locations")
    public String showLocations(Model model) {
        List<City> cityList = cityService.findAllCities();
        Map<String, List<String>> cityAddressMap = cityList.stream()
                .collect(Collectors.toMap( //Returns a Collector that accumulates elements into a Map whose keys and values are the result of applying the provided mapping functions to the input elements.
                        City::getName,  // key - name of city
                        city -> city.getLocationList().stream()
                                .map(Location::getAddress)  // value - list of addresses(List<Strings>)
                                .collect(Collectors.toList()) // the address list is going to the new list.
                ));


        model.addAttribute("addressesInCity", cityAddressMap);
        return "settings/locations";
    }

    @PostMapping("/locations")
    public String addLocation(@RequestParam(name = "newAddress") String newAddress, @RequestParam(name = "cityName") String cityName, Model model) {
        Location location = new Location(newAddress);
        City city = cityService.findCityByName(cityName);
        if (city != null) {
            location.setCity(city);
            try {
                locationService.saveLocation(location);
            } catch (DuplicateLocationException e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "settings/error";
            }
        }
        return "redirect:/admin/actions/locations";
    }


    @GetMapping("/locations/updateform/{address}/{cityName}")
    public String updateLocationForm(@PathVariable(value = "address")String address,@PathVariable(name = "cityName") String cName,Model model) {
        Location location =locationService.findLocationByAddress(address);
        List<UserDtoToShow> hosts = userService.findHostsByLocationId(location.getId());
        model.addAttribute("updloc",location);
        model.addAttribute("updcity",cName);
        model.addAttribute("hosts",hosts);
        return "settings/loc-update";
    }
    @PostMapping("/locations/update")
    public String updateLocation(@ModelAttribute(value = "updloc")Location location, @RequestParam(value = "updcity",required = false)String cName,
                                 @RequestParam(name = "locId",required = false)Integer locId,@RequestParam(name = "hostId",required = false)Integer hostId,
                                 @RequestParam(name = "query",required = false)String username,Model model) {
        if(hostId !=null&&locId==null){
            userService.deleteHostLocation(hostId);
        }
        if(username!=null&&locId!=null){
            try {
                userService.setHostLocation(locId,username);
            } catch (HostException e) {
                model.addAttribute("errorMessage",e.getMessage());
                return "settings/error";
            }
        }
        City city = cityService.findCityByName(cName);
        location.setCity(city);
        locationService.updateLocation(location);

        return "settings/loc-update";
    }


    @PostMapping("/locations/delete")
    public String deleteLocation(@RequestParam(name = "addressToDelete") String address) {
        locationService.deleteLocationByAddress(address);
        return "redirect:/admin/actions/locations";
    }

    @GetMapping("/users")
    public String showUsers(@RequestParam(name = "query", required = false) String username, Model model) {
        if (username == null) {
            return "settings/users";
        } else if (username != null) {
            try {
                UserDtoToShow dto = userService.getUserDtoToShow(username);
                model.addAttribute("requiredUser", dto);
                model.addAttribute("roles", Arrays.stream(Role.values()).filter(x->x!=dto.getRole()).collect(Collectors.toList()));
                return "/settings/users";
            } catch (NoResultException e) {
                model.addAttribute("errorMessage", "No such a user with that username: " + username);
                return "settings/error";
            }
        }
        return "settings/users";
    }

    @PostMapping("/users/delete")
    public ModelAndView deleteUser(@RequestParam(name = "userToDelete") String username) {
        ModelAndView modelAndView = new ModelAndView("settings/users");
        try {
            userService.deleteUserByUsername(username);
            modelAndView.addObject("result", "User with username " + username + " was deleted successfully");
            return modelAndView;
        } catch (AdminException e) {
            modelAndView.addObject("result", e.getMessage());
            return modelAndView;
        }
    }

    @PostMapping("/users/update")
    public String updateUser(@RequestParam(name = "selectedRole",required = false)Role newRole, @RequestParam(name = "userForRoleChange")String username,Model model){
            try {
                UserDtoToShow dto=userService.updateUserRole(username, newRole);
                model.addAttribute("newroleresult","User with username " + username + " now has a new role: "+newRole);
                model.addAttribute("updatedUser",dto);
                return "/settings/users";
            }catch (AdminException e){
                model.addAttribute("newroleresult", e.getMessage());
                return "/settings/users";
            }
    }

}
