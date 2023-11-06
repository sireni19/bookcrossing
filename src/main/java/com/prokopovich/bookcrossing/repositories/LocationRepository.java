package com.prokopovich.bookcrossing.repositories;

import com.prokopovich.bookcrossing.entities.Location;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;



public interface LocationRepository extends CrudRepository<Location, Integer> {

    Location findLocationByAddress(String address);
    @Modifying
    @Query("DELETE FROM Location l WHERE l.address = :ad")
    void deleteLoc(String ad);
}
