package com.prokopovich.bookcrossing.repositories;


import com.prokopovich.bookcrossing.entities.Location;
import com.prokopovich.bookcrossing.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);

    @Modifying
    @Query("DELETE FROM User u WHERE u.username = :un")
    void deleteUser(String un);

    Optional<List<User>> findUsersByLocationId(Integer id);

    @Modifying
    @Query("UPDATE User u SET u.location = NULL WHERE u.id = :userId")
    @Transactional
    void deleteHostLocation(@Param("userId") Integer id);

    @Modifying
    @Query("UPDATE User u SET u.location = :location WHERE u.username = :username")
    @Transactional
    void setHostLocation(@Param("location") Location location, @Param("username") String username);

    Optional<User> findUserByEmail(String email);

    User save(User user);

//    @Modifying
//    @Query("update User u set u.isActive=true where u.id=:number")
//    @Transactional
//    void activationById(@Param("number") Integer number);

}
