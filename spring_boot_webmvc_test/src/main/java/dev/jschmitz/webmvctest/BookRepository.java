package dev.jschmitz.webmvctest;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    public List<Book> findAll();
    public Optional<Book> findByIsbn(String isbn);
}
