package dev.jschmitz.jsontest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class ArticleJsonTest {

    @Autowired
    private JacksonTester<Article> json;

    @Test
    public void testSerialization() throws Exception {
        var article = new Article("Spring Boot 2", Ean.of("9783864905254"));

        assertThat(json.write(article)).extractingJsonPathValue("$.name").isEqualTo("Spring Boot 2");
        assertThat(json.write(article)).extractingJsonPathValue("$.ean").isEqualTo("9783864905254");
    }

    @Test
    public void testDeserialization() throws Exception {
        var jsonValue = "{\"name\":\"Spring Boot 2\",\"ean\":\"9783864905254\"}";
        var article = new Article("Spring Boot 2", Ean.of("9783864905254"));

        assertThat(json.parse(jsonValue)).usingRecursiveComparison().isEqualTo(article);
    }
}
