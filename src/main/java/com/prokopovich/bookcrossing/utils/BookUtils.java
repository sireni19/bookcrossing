package com.prokopovich.bookcrossing.utils;

import com.prokopovich.bookcrossing.entities.Book;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookUtils {
    public static Map<Integer,String> getBookPictures(List<Book> list) {
        Map<Integer, String> map=new HashMap<>();
        for (Book book : list) {
            byte[] img = book.getImage();
            if (img != null) {
                String base64Image = Base64.getEncoder().encodeToString(img);
                map.put(book.getId(), base64Image);
            }
        }
        return map;
    }
}
