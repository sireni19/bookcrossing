package com.prokopovich.bookcrossing.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;


@Data
@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 6_666_6_0L;
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
    private Role role = Role.USER_ROLE; //by default will be USER_ROLE
    @Column(name = "is_activate",nullable = true)
    private boolean isActivate =false;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.DETACH,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "user_id")
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;


}
