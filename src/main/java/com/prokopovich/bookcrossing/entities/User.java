package com.prokopovich.bookcrossing.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "encoded_password", nullable = false)
    private String password;
    private byte[] photo;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    private Book book;
    private Role role = Role.USER_ROLE; //by default will be USER_ROLE
}
