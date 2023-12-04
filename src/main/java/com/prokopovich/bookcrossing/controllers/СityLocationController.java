package com.prokopovich.bookcrossing.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prokopovich.bookcrossing.entities.Book;
import com.prokopovich.bookcrossing.entities.City;
import com.prokopovich.bookcrossing.entities.Location;
import com.prokopovich.bookcrossing.services.BookService;
import com.prokopovich.bookcrossing.utils.BookUtils;
import lombok.AllArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class СityLocationController {
    private CityControllerRest cityControllerRest;
    private LocationControllerRest locationControllerRest;

    @GetMapping("/cities")
    public String handleCityRequest(@RequestParam(value = "city", required = false, defaultValue = "all") String cityName, Model model) {
        List<City> cities = cityControllerRest.getAllCities();
        model.addAttribute("cities", cities);
        if (cityName != null && !cityName.equals("all")) {
            List<Location> locations = locationControllerRest.getLocationsInCity(cityName);
            Map<String, String> addressesCoordinates = locations.stream().collect(Collectors.toMap(Location::getAddress, loc -> loc.getCoordinates()));

            // Преобразование объекта addressesCoordinates в JSON-строку
            ObjectMapper objectMapper = new ObjectMapper();
            String addressesCoordinatesJson;
            try {
                addressesCoordinatesJson = objectMapper.writeValueAsString(addressesCoordinates);
                System.out.println(addressesCoordinatesJson);
            } catch (JsonProcessingException e) {
                addressesCoordinatesJson = "{}"; // Обработка ошибки преобразования в JSON
            }

            model.addAttribute("selectedCity", cityName);
            model.addAttribute("locations", locations);
            model.addAttribute("addressesCoordinatesJson", addressesCoordinatesJson);
        }
        return "locations";
    }

    @GetMapping("/shelves")
    public String showShelve(@RequestParam(name = "id") Integer id, Model model) {
        Location location = locationControllerRest.getLocationsById(id);
        List<Book> list = location.getBookList();
        list.removeIf(book -> book.getUser() != null);
        Map<Integer, String> pictures = BookUtils.getBookPictures(list);
        model.addAttribute("loc", location);
        model.addAttribute("coordinates", location.getCoordinates());
        model.addAttribute("address", location.getAddress());
        model.addAttribute("books", location.getBookList());
        model.addAttribute("pictures", pictures);
        return "shelves";
    }


}
