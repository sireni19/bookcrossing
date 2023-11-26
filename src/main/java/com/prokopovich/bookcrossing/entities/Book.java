package com.prokopovich.bookcrossing.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;

@Data
@Entity
@Table(name = "books")
@RequiredArgsConstructor
public class Book implements Serializable {
    private static final long serialVersionUID = 2_222_2_3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
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

    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User user;

    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;


    //TODO: add rating?
}
