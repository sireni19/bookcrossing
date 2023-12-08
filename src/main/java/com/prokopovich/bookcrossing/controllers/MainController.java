package com.prokopovich.bookcrossing.controllers;

import com.prokopovich.bookcrossing.entities.Book;
import com.prokopovich.bookcrossing.entities.News;
import com.prokopovich.bookcrossing.services.BookService;
import com.prokopovich.bookcrossing.services.CityService;
import com.prokopovich.bookcrossing.services.NewsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class MainController {
    private final CityService cityService;
    private final BookService bookService;
    private final NewsService newsService;

    @GetMapping("/main")
    public String main(Model model) {
        List<News> news = newsService.getAllNews();
        model.addAttribute("news",news);
        return "main";
    }

    @GetMapping("/books")
    public String showActionsPage(Model model,
                                  @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(name = "city", required = false,defaultValue = "all") String city) {
        try {
            Page<Book> pageBooks;
            List<Book> list;
            Map<Integer, String> map = new HashMap<>();

            if (city != null && !city.isEmpty() && !"all".equals(city)) {
                pageBooks = bookService.getAllBookInCity(city, PageRequest.of(page, 2, Sort.Direction.DESC, "id"));
                model.addAttribute("selectedCity", city);
            } else {
                pageBooks = bookService.getAllBooks(PageRequest.of(page, 2, Sort.Direction.DESC, "id"));
            }

            list = pageBooks.getContent();

            for (Book book : list) {
                byte[] img = book.getImage();
                if (img != null) {
                    String base64Image = Base64.getEncoder().encodeToString(img);
                    map.put(book.getId(), base64Image);
                }
            }

            model.addAttribute("cities", cityService.findAllCities());
            model.addAttribute("bookImagesMap", map);
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

