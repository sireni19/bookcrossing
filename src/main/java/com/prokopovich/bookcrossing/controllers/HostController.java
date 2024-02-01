package com.prokopovich.bookcrossing.controllers;

import com.prokopovich.bookcrossing.entities.*;
import com.prokopovich.bookcrossing.exceptions.DuplicateBookException;
import com.prokopovich.bookcrossing.services.AuthorService;
import com.prokopovich.bookcrossing.services.BookService;
import com.prokopovich.bookcrossing.services.SubgenreService;
import com.prokopovich.bookcrossing.services.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/host/actions")
@AllArgsConstructor
@SessionAttributes(value = "hostLocation")
public class HostController {
    private AuthorService authorService;
    private SubgenreService subgenreService;
    private BookService bookService;

    /*если какому-то из методов понадобится Location,
    то сработает метод createLocation из utils.HostLocationAspect, поместится в сессию и из сессии его можно будет забирать*/
    @GetMapping("/main")
    public String showActionsPage(Model model,
                                  @RequestParam(value = "page", defaultValue = "0") Integer page, Authentication authentication, HttpSession httpSession) {

        try {
            Page<Book> pageBooks = bookService.findAllBooksInLocation((Location) httpSession.getAttribute("hostLocation"), PageRequest.of(page, 3, Sort.Direction.DESC, "id"));
            List<Book> bookList = pageBooks.getContent();
            Map<Integer, String> map = new HashMap<>();
            for (Book book : bookList) {
                byte[] img = book.getImage();
                if (img != null) {
                    String base64Image = Base64.getEncoder().encodeToString(img);
                    map.put(book.getId(), base64Image);
                }
            }
            model.addAttribute("bookImagesMap", map);
            model.addAttribute("booksOnPage", pageBooks);
            model.addAttribute("numbers", IntStream.range(0, pageBooks.getTotalPages()).boxed());
            return "management/host-full-functional";
        } catch (EntityNotFoundException e) {
            String errorMessage = e.getMessage();
            return "redirect:/host/actions/main?fail=" + errorMessage;
        }
    }

    @GetMapping("/books")
    public String showBookCreationPage(Model model) {
        List<Author> authorList = authorService.getAllAuthors();
        List<Subgenre> subgenreList = subgenreService.getAllSubgenres();
        Book book = new Book();
        model.addAttribute("newBook", book);
        model.addAttribute("authors", authorList);
        model.addAttribute("subgenres", subgenreList);
        return "management/book-creation";
    }

    @PostMapping("/books/new")
    public String addBook(@ModelAttribute(name = "newBook") Book book, @RequestParam(name = "img") MultipartFile image, HttpSession session, Model model,
                          @RequestParam(name = "author") String authorId, @RequestParam(name = "subgenre") String subgenreId) {
        try {
            bookService.addBook(book, authorId, subgenreId, image, session);
        } catch (DuplicateBookException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "settings/error";
        }
        return "redirect:/host/actions/books";
    }

    @PostMapping("/addAuthor")
    public String addAuthor(@RequestParam(name = "newAuthorName") String name, Model model) {
        try {
            authorService.addAuthor(name);
            return "redirect:/host/actions/books";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "settings/error";
        }
    }

    @PostMapping("/addSubgenre")
    public String addSubgenre(@RequestParam(name = "newSubgenreName") String name, Model model) {
        try {
            subgenreService.addSubgenre(name);
            return "redirect:/host/actions/books";
        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "settings/error";
        }
    }

    @GetMapping("/give")
    public String showGiveBookForm() {
        return "management/give-book";
    }

    @GetMapping("/find")
    public String findGiveBook(@RequestParam("title1") String title, @RequestParam("isbn1") String isbn,
                               HttpSession httpSession, Model model) {
        Book book = bookService.findBookByTitleIsbnAndLocation(title.trim(), isbn, (Location) httpSession.getAttribute("hostLocation"));
        if (book != null) {
            if (book.getImage() != null) {
                String base64Image = Base64.getEncoder().encodeToString(book.getImage());
                model.addAttribute("base64Image", base64Image);
            }
            model.addAttribute("foundBook", book);
            return "management/give-book";
        } else {
            return "redirect:/host/actions/give?fail";
        }
    }

    @PutMapping("/setuser/{bookId}")
    public String updateBookSetUser(@PathVariable Integer bookId, @RequestParam(name = "toUser") String username, Model model) {
        try {
            bookService.setUserToBook(username, bookId);
            model.addAttribute("message", "Книга успешно обновлена");
            return "redirect:/host/actions/give";
        } catch (EntityNotFoundException e) {
            return "redirect:/host/actions/give?fail2";
        }
    }

    @GetMapping("/return")
    public String showReturnBookForm(Model model) {
        return "management/return-book";
    }

    @GetMapping("/find2")
    public String findUserBook(@RequestParam(name = "useremail") String email, Model model,HttpSession httpSession) {
        try {
            Book book = bookService.findBookByUserEmail(email);
            if (book != null) {
                bookService.clearUserSetNewLocationToBook((Location) httpSession.getAttribute("hostLocation"), book.getId());
                return "management/return-book";
            } else {
                model.addAttribute("fail", "У этого пользователя нет книги");
                return "management/return-book";
            }
        } catch (EntityNotFoundException e) {
            String errorMessage = e.getMessage();
            model.addAttribute("fail", errorMessage);
            return "management/return-book";
        }

    }

    @GetMapping("/updateform{id}")
    public String showUpdateFormFull(@RequestParam(name = "id") Integer id, Model model) {
        List<Author> authorList = authorService.getAllAuthors();
        List<Subgenre> subgenreList = subgenreService.getAllSubgenres();
        Book book = bookService.findBookById(id);
        model.addAttribute("updBook", book);
        model.addAttribute("authors", authorList);
        model.addAttribute("subgenres", subgenreList);
        return "management/update-form";
    }

    @PutMapping("/books")
    public String updateBook(@RequestParam("id") Integer bookId, @ModelAttribute(name = "updBook") Book updatedBook,
                             @RequestParam(name = "img", required = false) MultipartFile image,
                             @RequestParam(name = "author", required = false) String authorId, @RequestParam(name = "subgenre", required = false) String subgenreId,
                             HttpSession session) {
        bookService.updateBook(updatedBook, bookId, image, authorId, subgenreId, session);

        return "redirect:/host/actions/main";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable(name = "id") Integer id) {
        bookService.deleteBookById(id);
        return "redirect:/host/actions/main";
    }

}

