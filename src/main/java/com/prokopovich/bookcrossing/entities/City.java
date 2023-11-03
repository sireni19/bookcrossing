package com.prokopovich.bookcrossing.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.util.List;

@Data
@Entity
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "city_name", nullable = false, unique = true)
    private String name;


    @OneToMany(cascade =CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name = "city_id") // uni-directional relation
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private List<Location> locationList;
}
