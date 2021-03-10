package dev.jschmitz.jsontest;

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
        var book = new Book("Spring Boot 2", "Michael Simons", "978-3-86490-525-4");
        var expectedJson = "{\"title\":\"Spring Boot 2\",\"author\":\"Michael Simons\",\"isbn\":\"978-3-86490-525-4\"}";

        assertThat(json.write(book)).isEqualToJson(expectedJson);
    }

    @Test
    public void testSerializationWithSingleFields() throws Exception {
        var book = new Book("Spring Boot 2", "Michael Simons", "978-3-86490-525-4");

        assertThat(json.write(book)).hasJsonPathValue("$.title", "Spring Boot 2");
        assertThat(json.write(book)).hasJsonPathValue("$.author", "Michael Simons");
        assertThat(json.write(book)).hasJsonPathValue("$.isbn", "978-3-86490-525-4");
    }

    @Test
    public void testDeserialization() throws Exception {
        var jsonValue = "{\"title\":\"Spring Boot 2\",\"author\":\"Michael Simons\",\"isbn\":\"978-3-86490-525-4\"}";
        var expectedBook = new Book("Spring Boot 2", "Michael Simons", "978-3-86490-525-4");

        assertThat(json.parse(jsonValue)).usingRecursiveComparison().isEqualTo(expectedBook);
    }
}
