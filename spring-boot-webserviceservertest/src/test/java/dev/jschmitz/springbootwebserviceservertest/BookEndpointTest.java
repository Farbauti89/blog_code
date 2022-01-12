package dev.jschmitz.springbootwebserviceservertest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import dev.jschmitz.spring_boot_webserviceservertest.webservice.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.noFault;
import static org.springframework.ws.test.server.ResponseMatchers.serverOrReceiverFault;
import static org.springframework.ws.test.server.ResponseMatchers.xpath;

@WebServiceServerTest
class BookEndpointTest {

    private static final Map<String, String> NAMESPACE_MAPPING = Map.ofEntries(
            Map.entry("js", "http://jschmitz.dev/spring-boot-webserviceservertest/webservice/model")
    );

    @Autowired
    private MockWebServiceClient client;

    @MockBean
    private BookRepository bookRepository;

    private static Book createBook(String isbn, String title, String author) {
        var book = new Book();
        book.setIsbn(isbn);
        book.setTitle(title);
        book.setAuthor(author);

        return book;
    }

    @Test
    void returnsListOfBooks() {

        var testBook = createBook("978-3-86490-525-4", "Spring Boot 2", "Michael Simons");
        var testBook2 = createBook("978-3-86490-673-2", "Sustainable Software Architecture", "Carola Lilienthal");

        when(bookRepository.findAll()).thenReturn(List.of(testBook, testBook2));

        final var request = "<js:getBooksRequest"+
                            "   xmlns:js=\"http://jschmitz.dev/spring-boot-webserviceservertest/webservice/model\""+
                            "/>";

        this.client
                .sendRequest(withPayload(new StringSource(request)))
                .andExpect(noFault())
                .andExpect(xpath("/js:getBooksResponse/js:books[1]/js:isbn", NAMESPACE_MAPPING).evaluatesTo(testBook.getIsbn()))
                .andExpect(xpath("/js:getBooksResponse/js:books[1]/js:title", NAMESPACE_MAPPING).evaluatesTo(testBook.getTitle()))
                .andExpect(xpath("/js:getBooksResponse/js:books[1]/js:author", NAMESPACE_MAPPING).evaluatesTo(testBook.getAuthor()))
                .andExpect(xpath("/js:getBooksResponse/js:books[2]", NAMESPACE_MAPPING).exists());
    }

    @Test
    void returnsBook() {

        var testBook = createBook("978-3-86490-673-2", "Sustainable Software Architecture", "Carola Lilienthal");
        when(bookRepository.findByIsbn(testBook.getIsbn())).thenReturn(Optional.of(testBook));

        final String request = """
                       <js:getBookRequest
                            xmlns:js="http://jschmitz.dev/spring-boot-webserviceservertest/webservice/model">
                            <js:isbn>978-3-86490-673-2</js:isbn>
                       </js:getBookRequest>
                """;

        this.client
                .sendRequest(withPayload(new StringSource(request)))
                .andExpect(noFault())
                .andExpect(xpath("/js:getBookResponse/js:book/js:isbn", NAMESPACE_MAPPING).evaluatesTo(testBook.getIsbn()))
                .andExpect(xpath("/js:getBookResponse/js:book/js:title", NAMESPACE_MAPPING).evaluatesTo(testBook.getTitle()))
                .andExpect(xpath("/js:getBookResponse/js:book/js:author", NAMESPACE_MAPPING).evaluatesTo(testBook.getAuthor()));
    }

    @Test
    void returnsFaultMessage_IfThereIsNoBookWithTheGivenId() {

        when(bookRepository.findByIsbn(any(String.class))).thenReturn(Optional.empty());

        final var isbn = "978-3-86490-673-2";
        final var request = """
                       <js:getBookRequest
                            xmlns:js="http://jschmitz.dev/spring-boot-webserviceservertest/webservice/model">
                            <js:isbn>%s</js:isbn>
                       </js:getBookRequest>
                """.formatted(isbn);

        this.client
                .sendRequest(withPayload(new StringSource(request)))
                .andExpect(serverOrReceiverFault("Can not find Book with ISBN '%s'".formatted(isbn)));
    }
}