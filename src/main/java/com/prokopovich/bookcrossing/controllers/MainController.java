package com.prokopovich.bookcrossing.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/books")
    public String books() {
        return "books";
    }

    @GetMapping("/head")
    public String header() {
        return "fragments/header";
    }
}
