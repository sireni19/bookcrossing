package com.prokopovich.bookcrossing.servicesimpl;

import com.prokopovich.bookcrossing.entities.City;
import com.prokopovich.bookcrossing.entities.Location;
import com.prokopovich.bookcrossing.exceptions.DuplicateLocationException;
import com.prokopovich.bookcrossing.geo.Geocoder;
import com.prokopovich.bookcrossing.repositories.CityRepository;
import com.prokopovich.bookcrossing.repositories.LocationRepository;
import com.prokopovich.bookcrossing.services.LocationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;


@Service

public class LocationServiceImpl implements LocationService {
    private LocationRepository locationRepository;
    private CityRepository cityRepository;
    private Geocoder geocoder;


    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, CityRepository cityRepository, Geocoder geocoder) {
        this.locationRepository = locationRepository;
        this.cityRepository = cityRepository;
        this.geocoder=geocoder;
    }

    @Override
    public void saveLocation(String newAddress, String name, String cityName, String description) throws DuplicateLocationException {
        if (locationRepository.findLocationByAddress(newAddress.trim()) == null) {
            Location location = new Location(newAddress);
            City city = cityRepository.findCityByName(cityName);
            String fullAddress = cityName + ", " + newAddress;
            if (city != null) {
                location.setCity(city);
            }
            location.setName(name);
            String response = null;
            try {
                response = geocoder.GeocodeSync(fullAddress);
                String coordinates = geocoder.extractCoordinates(response);
                location.setCoordinates(coordinates);
                location.setDescription(description);
                locationRepository.save(location);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new DuplicateLocationException("Such location has been already existed");
        }
    }

    @Override
    @Transactional
    public void deleteLocationByAddress(String address) {
        locationRepository.deleteLoc(address);
    }


    @Override
    public Location findLocationByAddress(String address) {
        return locationRepository.findLocationByAddress(address);
    }

    @Override
    @Transactional
    public void updateLocation(Location loc) {
        locationRepository.updateLocation(loc.getId(), loc.getAddress(),loc.getDescription());
    }

    @Override
    public List<Location> findLocationsByCity(String cityName) {
        City city = cityRepository.findCityByName(cityName);
        return  locationRepository.findLocationsByCity(city);
    }

    @Override
    public List<Location> findAllLocations() {
        return (List<Location>) locationRepository.findAll();
    }

    @Override
    public Location getLocationById(Integer id) {
        return locationRepository.findById(id).get();
    }
}
