package com.prokopovich.bookcrossing.controllers;

import com.prokopovich.bookcrossing.dto.BookDto;
import com.prokopovich.bookcrossing.services.BookService;
import com.prokopovich.bookcrossing.services.CityService;
import com.prokopovich.bookcrossing.utils.BookUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class BookController {
    private final CityService cityService;
    private final BookService bookService;

    @GetMapping("/books")
    public String showActionsPage(Model model,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(name = "city", required = false, defaultValue = "all") String city,
                                  @RequestParam(name = "query", required = false) String query) {
        try {
            Page<BookDto> pageBooks;
            List<BookDto> list;
            Map<Integer, String> pictures;
            if (city != null && !city.isEmpty() && !"all".equals(city)) {
                if (query != null) {
                    pageBooks = bookService.getAllBooksWithTitleLike(query, PageRequest.of(page, 100, Sort.Direction.DESC, "id"));
                    model.addAttribute("selectedCity", city);
                } else {
                    pageBooks = bookService.getAllBookInCity(city, PageRequest.of(page, 6, Sort.Direction.DESC, "id"));
                    model.addAttribute("selectedCity", city);
                }
            } else {
                if (query != null) {
                    pageBooks = bookService.getAllBooksWithTitleLike(query, PageRequest.of(page, 100, Sort.Direction.DESC, "id"));
                } else {
                    pageBooks = bookService.getAllBooks(PageRequest.of(page, 6, Sort.Direction.DESC, "id"));
                }
            }

            list = pageBooks.getContent();

            pictures = BookUtils.getBookPictures2(list);

            model.addAttribute("cities", cityService.findAllCities());
            model.addAttribute("bookImagesMap", pictures);
            model.addAttribute("booksOnPage", pageBooks);
            model.addAttribute("numbers", IntStream.range(0, pageBooks.getTotalPages()).boxed());
            return "books";
        } catch (EntityNotFoundException e) {
            String errorMessage = e.getMessage();
            model.addAttribute("errorMessage", errorMessage);
            return "settings/error";
        }
    }
}
