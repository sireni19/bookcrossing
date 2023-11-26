package com.prokopovich.bookcrossing.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.List;
@Data
@Entity
@Table(name = "locations")
@NoArgsConstructor
public class Location implements Serializable {
    private static final long serialVersionUID = 5_555_5_1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @NotBlank
    @Size(min = 5,max=64)
    private String address;

    @OneToMany(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "location_id")
    private List<Book> bookList;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @OneToMany(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "location_id")
    private List<User> userList;

    public Location(String address) {
        this.address = address;
    }

}
