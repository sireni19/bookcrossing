package com.prokopovich.bookcrossing.controllers;

import com.prokopovich.bookcrossing.entities.*;
import com.prokopovich.bookcrossing.exceptions.DuplicateBookException;
import com.prokopovich.bookcrossing.services.AuthorService;
import com.prokopovich.bookcrossing.services.BookService;
import com.prokopovich.bookcrossing.services.SubgenreService;
import com.prokopovich.bookcrossing.services.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/host/actions")
@AllArgsConstructor
public class HostController {
    private AuthorService authorService;
    private SubgenreService subgenreService;
    private UserService userService;
    private BookService bookService;

    @GetMapping("")
    public String showActionsPage() {
        return "management/host-full-functional";
    }

    @GetMapping("/books")
    public String showBookCreationPage(Model model, Authentication authentication, HttpSession httpSession) {
        List<Author> authorList = authorService.getAllAuthors();
        List<Subgenre> subgenreList = subgenreService.getAllSubgenres();
        String hostname = authentication.getName();
        User host = userService.findUserByUsername(hostname);
        Location hostLocation = host.getLocation();
        Book book = new Book();
        model.addAttribute("newBook", book);
        model.addAttribute("authors", authorList);
        model.addAttribute("subgenres", subgenreList);
        httpSession.setAttribute("hostLocation", hostLocation);
        return "management/book-creation";
    }

    @PostMapping("/books/new")
    public String addBook(@ModelAttribute(name = "newBook") Book book, @RequestParam(name = "img") MultipartFile image, HttpSession session, Model model,
                          @RequestParam(name = "author")String authorId,@RequestParam(name = "subgenre")String subgenreId) {
        if (!image.isEmpty()) {
            try {
                byte[] bytes = image.getBytes();
                book.setImage(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Location bookLocation = (Location) session.getAttribute("hostLocation");
        Author author1 = authorService.getAuthorById(Integer.parseInt(authorId));
        Subgenre subgenre1 = subgenreService.getSubgenreById(Integer.parseInt(subgenreId));
        book.setLocation(bookLocation);
        book.setAuthor(author1);
        book.setSubgenre(subgenre1);
        try {
            bookService.addBook(book);
        } catch (DuplicateBookException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "settings/error";
        }
        return "management/book-creation";
    }

    @PostMapping("/addAuthor")
    public String addAuthor(@RequestParam(name = "newAuthorName") String name, Model model) {
        try {
            Author author = new Author(name.trim());
            authorService.addAuthor(author);
            return "redirect:/host/actions/books";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "settings/error";
        }
    }

    @PostMapping("/addSubgenre")
    public String addSubgenre(@RequestParam(name = "newSubgenreName") String name, Model model) {
        try {
            Subgenre subgenre = new Subgenre(name.trim());
            subgenreService.addSubgenre(subgenre);
            return "redirect:/host/actions/books";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "settings/error";
        }
    }
}

