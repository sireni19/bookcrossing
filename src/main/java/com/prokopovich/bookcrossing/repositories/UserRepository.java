package com.prokopovich.bookcrossing.repositories;


import com.prokopovich.bookcrossing.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);

    @Modifying
    @Query("DELETE FROM User u WHERE u.username = :un")
    void deleteUser(String un);
    User save(User user);


}
