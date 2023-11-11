package com.prokopovich.bookcrossing.repositories;

import com.prokopovich.bookcrossing.entities.Location;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface LocationRepository extends CrudRepository<Location, Integer> {

    Location findLocationByAddress(String address);

    @Modifying
    @Query("DELETE FROM Location l WHERE l.address = :ad")
    void deleteLoc(String ad);

    @Modifying
    @Query("UPDATE Location l SET l.address = :address WHERE l.id = :id")
    void updateLocation(@Param("id") Integer id, @Param("address") String address);
}

