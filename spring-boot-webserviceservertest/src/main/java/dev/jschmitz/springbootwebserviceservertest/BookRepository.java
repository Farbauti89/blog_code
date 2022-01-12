package dev.jschmitz.springbootwebserviceservertest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import dev.jschmitz.spring_boot_webserviceservertest.webservice.model.Book;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository{

    private final List<Book> books = new ArrayList<>();

    public BookRepository() {
        var springBoot = new Book();
        springBoot.setTitle("Spring Boot 2");
        springBoot.setAuthor("Michael Simons");
        springBoot.setIsbn("978-3-86490-525-4");

        books.add(springBoot);

        var sustainableSoftwareArchitecture = new Book();
        sustainableSoftwareArchitecture.setTitle("Sustainable Software Architecture");
        sustainableSoftwareArchitecture.setAuthor("Carola Lilienthal");
        sustainableSoftwareArchitecture.setIsbn("978-3-86490-673-2");

        books.add(sustainableSoftwareArchitecture);
    }

    public List<Book> findAll() {
        return Collections.unmodifiableList(books);
    }

    public Optional<Book> findByIsbn(String isbn) {
        return books.stream().filter(book -> book.getIsbn().equals(isbn)).findFirst();
    }
}