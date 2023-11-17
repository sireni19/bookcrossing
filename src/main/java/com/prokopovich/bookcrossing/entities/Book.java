package com.prokopovich.bookcrossing.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@Table(name = "books")

@RequiredArgsConstructor
public class Book implements Serializable {
    private static final long serialVersionUID = 2_222_2_0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = true)
    @Size(min = 13,max = 13)
    private String isbn;
    @Column(nullable = false)
    private String title;

    private byte[] image;
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "genre_id")
    private Subgenre subgenre;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "location_id")
    private Location location;

    //TODO: add rating?
}
