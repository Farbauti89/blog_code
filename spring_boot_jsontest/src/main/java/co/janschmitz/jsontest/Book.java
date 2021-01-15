package co.janschmitz.jsontest;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class Book {

    private String title;
    private String author;
    @JsonUnwrapped
    private Isbn isbn;

    private Book() {
    }

    public Book(String title, String author, Isbn isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public void setIsbn(Isbn isbn) {
        this.isbn = isbn;
    }
}
