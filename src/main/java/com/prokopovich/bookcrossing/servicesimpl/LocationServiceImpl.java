package com.prokopovich.bookcrossing.servicesimpl;

import com.prokopovich.bookcrossing.entities.Location;
import com.prokopovich.bookcrossing.exceptions.DuplicateLocationException;
import com.prokopovich.bookcrossing.repositories.LocationRepository;
import com.prokopovich.bookcrossing.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class LocationServiceImpl implements LocationService {
    private LocationRepository locationRepository;


    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public void saveLocation(Location location) throws DuplicateLocationException {
        if (locationRepository.findLocationByAddress(location.getAddress().trim()) == null) {
            locationRepository.save(location);
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
        locationRepository.updateLocation(loc.getId(), loc.getAddress());
    }

}
