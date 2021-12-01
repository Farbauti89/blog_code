package dev.jschmitz.webmvctest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryBookRepository implements BookRepository {

    private final List<Book> books = List.of(
            new Book("Spring Boot 2", "Michael Simons", "978-3-86490-525-4")
            , new Book("Langlebige Software-Architekturen", "Carola Lilienthal", "978-3-86490-729-6")
    );

    @Override
    public List<Book> findAll() {
        return Collections.unmodifiableList(books);
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return books.stream().filter(book -> book.getIsbn().equals(isbn)).findFirst();
    }
}
