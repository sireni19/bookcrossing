package com.prokopovich.bookcrossing.controllers;

import com.prokopovich.bookcrossing.entities.News;
import com.prokopovich.bookcrossing.services.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {
    private final NewsService newsService;

    @GetMapping("/main")
    public String main(Model model) {
        List<News> news = newsService.getAllNews();
        model.addAttribute("news",news);
        return "main";
    }


}

