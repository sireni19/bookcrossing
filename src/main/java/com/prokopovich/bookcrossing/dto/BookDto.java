package com.prokopovich.bookcrossing.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDto {
    private Integer id;
    private String title;
    private String isbn;
    private byte [] image;
    private String author;
    private String subgenre;
    private String user;
    private String location;
}
