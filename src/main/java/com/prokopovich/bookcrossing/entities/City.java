package com.prokopovich.bookcrossing.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @Size(min = 1,max=30)
    @NotBlank
    private String name;

    public City(String name) {
        this.name = name;
    }

    @OneToMany(cascade =CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name = "city_id") // uni-directional relation
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private List<Location> locationList;

    public City() {

    }
}
