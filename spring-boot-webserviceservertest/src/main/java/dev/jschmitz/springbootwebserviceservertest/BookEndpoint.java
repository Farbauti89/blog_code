package dev.jschmitz.springbootwebserviceservertest;

import dev.jschmitz.spring_boot_webserviceservertest.webservice.model.GetBookRequest;
import dev.jschmitz.spring_boot_webserviceservertest.webservice.model.GetBookResponse;
import dev.jschmitz.spring_boot_webserviceservertest.webservice.model.GetBooksResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class BookEndpoint {

    private static final String NAMESPACE_URI = "http://jschmitz.dev/spring-boot-webserviceservertest/webservice/model";

    private final BookRepository bookRepository;

    public BookEndpoint(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBooksRequest")
    @ResponsePayload
    public GetBooksResponse getBooks() {

        var books = bookRepository.findAll();

        var response = new GetBooksResponse();
        response.getBooks().addAll(books);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getBookRequest")
    @ResponsePayload
    public GetBookResponse getBookByIsbn(@RequestPayload GetBookRequest request) {

        var book = bookRepository.findByIsbn(request.getIsbn())
                .orElseThrow(() -> new BookNotFoundException("Can not find Book with ISBN '%s'".formatted(request.getIsbn())));

        var response = new GetBookResponse();
        response.setBook(book);

        return response;
    }
}
