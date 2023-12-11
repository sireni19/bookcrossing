package com.prokopovich.bookcrossing.utils;

import com.prokopovich.bookcrossing.dto.BookDto;
import com.prokopovich.bookcrossing.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BookUtils {
    public static Map<Integer, String> getBookPictures(List<Book> list) {
        Map<Integer, String> map = new HashMap<>();
        for (Book book : list) {
            byte[] img = book.getImage();
            if (img != null) {
                String base64Image = Base64.getEncoder().encodeToString(img);
                map.put(book.getId(), base64Image);
            }
        }
        return map;
    }

    public static Map<Integer, String> getBookPictures2(List<BookDto> list) {
        Map<Integer, String> map = new HashMap<>();
        for (BookDto book : list) {
            byte[] img = book.getImage();
            if (img != null) {
                String base64Image = Base64.getEncoder().encodeToString(img);
                map.put(book.getId(), base64Image);
            }
        }
        return map;
    }

    public static synchronized BookDto convertFromBookToDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setImage(book.getImage());
        dto.setAuthor(book.getAuthor().getName());
        dto.setSubgenre(book.getSubgenre().getName());
        dto.setLocation(book.getLocation().getAddress());
        return dto;
    }

    public static Page<BookDto> pageBooksToDtoPage(Page<Book> bookPage) {
        Page<BookDto> bookDtoPage=bookPage.map(b->convertFromBookToDto(b));
        return bookDtoPage;
    }

    public static List<BookDto> listBooksToDtoList(List<Book> books) {
        List<BookDto> bookDtoList = new ArrayList<>();
        books.stream().forEach(b -> {
            BookDto dto = convertFromBookToDto(b);
            bookDtoList.add(dto);
        });
        return bookDtoList;
    }
}
