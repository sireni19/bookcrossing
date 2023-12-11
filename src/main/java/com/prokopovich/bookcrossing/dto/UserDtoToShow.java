package com.prokopovich.bookcrossing.dto;

import com.prokopovich.bookcrossing.entities.Location;
import com.prokopovich.bookcrossing.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDtoToShow {
    private Integer id;
    private String username;
    private String email;
    private Role role;
    private Location location;
    //TODO need to add the relation with Book for HOSTs
}
