package com.prokopovich.bookcrossing.services;

import com.prokopovich.bookcrossing.entities.Location;
import com.prokopovich.bookcrossing.exceptions.DuplicateLocationException;



public interface LocationService {

    void saveLocation(Location location) throws DuplicateLocationException;
    void deleteLocationByAddress(String address);
    Location findLocationByAddress(String name);
    void updateLocation(Location loc);

}
