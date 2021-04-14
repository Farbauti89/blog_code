package dev.jschmitz.webmvctest;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @GetMapping(path = "/{isbn}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> findByIsbn(@PathVariable String isbn) {
        var bookOptional = bookRepository.findByIsbn(isbn);
        if (bookOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(bookOptional.get());
    }

}
