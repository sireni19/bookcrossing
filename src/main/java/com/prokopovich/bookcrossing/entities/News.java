package com.prokopovich.bookcrossing.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "news")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String pictureLink;
    private String content;
    private String date;
    public News(String title, String pictureLink, String content,String date) {
        this.title = title;
        this.pictureLink = pictureLink;
        this.content = content;
        this.date= date;
    }
}
