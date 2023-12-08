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


}

