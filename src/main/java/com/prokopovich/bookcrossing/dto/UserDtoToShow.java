package com.prokopovich.bookcrossing.dto;

import com.prokopovich.bookcrossing.entities.Role;
import lombok.Data;

@Data
public class UserDtoToShow {

    private String username;
    private String email;
    private Role role;
    private boolean isActive;
    //TODO need to add the relation with Book for HOSTs
}
