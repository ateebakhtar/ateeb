package com.example.ateeb.Controllers;
import java.util.Arrays;
import java.util.List;

import com.example.ateeb.Models.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BooksController {
    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return Arrays.asList(
                new Book(1l, "Mastering Spring 5.2", "Ranga Karanam"));
    }
}
