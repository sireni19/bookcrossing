package com.prokopovich.bookcrossing.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Data
@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "genre_name", nullable = false)
    private String name;

    @OneToMany
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "genre_id")
    private List<Book> bookList;

}
