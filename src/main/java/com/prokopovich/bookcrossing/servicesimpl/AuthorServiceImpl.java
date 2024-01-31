package com.prokopovich.bookcrossing.servicesimpl;

import com.prokopovich.bookcrossing.entities.Author;
import com.prokopovich.bookcrossing.repositories.AuthorRepository;
import com.prokopovich.bookcrossing.services.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAllOrderByAuthorAsc();
    }

    @Override
    public void addAuthor(String authorName) throws DataIntegrityViolationException {
        Author author = new Author(authorName);
        authorRepository.save(author);
    }

    @Override
    public Author getAuthorById(Integer id) {
        return authorRepository.findAuthorById(id);
    }
}
