package dev.jschmitz.webmvctest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
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
        var book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> BookNotFoundException.unknownIsbn(isbn));

        return ResponseEntity.ok(book);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private static class BookNotFoundException extends RuntimeException {

        private BookNotFoundException(String message) {
            super(message);
        }

        public static BookNotFoundException unknownIsbn(String isbn){
            return new BookNotFoundException("Can not find book with ISBN '%s'".formatted(isbn));
        }
    }

}
