package co.janschmitz.jsontest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTest {

    @Autowired
    private JacksonTester<Book> json;

    @Test
    public void testSerialization() throws Exception {
        var book = new Book("Spring Boot 2", "Michael Simons", Isbn.of("978-3-86490-525-4"));

        assertThat(json.write(book)).extractingJsonPathValue("$.title").isEqualTo("Spring Boot 2");
        assertThat(json.write(book)).extractingJsonPathValue("$.author").isEqualTo("Michael Simons");
        assertThat(json.write(book)).extractingJsonPathValue("$.isbn").isEqualTo("978-3-86490-525-4");
    }

    @Test
    public void testDeserialization() throws Exception {
        var jsonValue = "{\"title\":\"Spring Boot 2\",\"author\":\"Michael Simons\",\"isbn\":\"978-3-86490-525-4\"}";
        var book = json.parse(jsonValue).getObject();

        assertThat(book.getTitle()).isEqualTo("Spring Boot 2");
        assertThat(book.getAuthor()).isEqualTo("Michael Simons");
        assertThat(book.getIsbn().getValue()).isEqualTo("978-3-86490-525-4");
    }
}
