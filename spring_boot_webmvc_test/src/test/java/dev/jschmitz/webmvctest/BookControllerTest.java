package dev.jschmitz.webmvctest;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({BookController.class})
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void returnsListOfBooks() throws Exception {
        var books = List.of(new Book("Spring Boot 2", "Michael Simons", "978-3-86490-525-4")
                , new Book("Langlebige Software-Architekturen", "Carola Lilienthal", "978-3-86490-729-6"));

        doReturn(books).when(bookRepository).findAll();

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].title", is(books.get(0).getTitle())))
                .andExpect(jsonPath("$.[0].author", is(books.get(0).getAuthor())))
                .andExpect(jsonPath("$.[0].isbn", is(books.get(0).getIsbn())));
    }

    @Test
    void returnsBook() throws Exception {
        var book = new Book("Langlebige Software-Architekturen", "Carola Lilienthal", "978-3-86490-729-6");
        doReturn(Optional.of(book)).when(bookRepository).findByIsbn(eq(book.getIsbn()));

        mockMvc.perform(get("/".concat(book.getIsbn())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is(book.getTitle())))
                .andExpect(jsonPath("$.author", is(book.getAuthor())))
                .andExpect(jsonPath("$.isbn", is(book.getIsbn())));
    }

    @Test
    void returns404_WhenThereIsNoBookWithTheGivenIsbn() throws Exception {
        doReturn(Optional.empty()).when(bookRepository).findByIsbn(anyString());

        mockMvc.perform(get("/123-4-56789-123-3"))
                .andExpect(status().isNotFound());
    }
}
