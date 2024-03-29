package com.prokopovich.bookcrossing.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "subgenres")
@NoArgsConstructor
@AllArgsConstructor
public class Subgenre implements Serializable {
    private static final long serialVersionUID = 4_444_4_0L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "genre_name", nullable = false)
    private String name;

    @OneToMany
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "genre_id")
    private List<Book> bookList;

    public Subgenre(String name) {
        this.name = name;
    }
}
