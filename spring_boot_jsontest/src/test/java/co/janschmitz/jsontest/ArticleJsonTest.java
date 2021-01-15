package co.janschmitz.jsontest;

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
        Isbn isbn = Isbn.of("978-3-86490-525-4");
        var article = new Article("Spring Boot 2", Ean.of("9783864905254"));

        assertThat(json.write(article)).extractingJsonPathValue("$.name").isEqualTo("Spring Boot 2");
        assertThat(json.write(article)).extractingJsonPathValue("$.ean").isEqualTo("9783864905254");
    }

    @Test
    public void testDeserialization() throws Exception {
        var jsonValue = "{\"name\":\"Spring Boot 2\",\"ean\":\"9783864905254\"}";
        var article = json.parse(jsonValue).getObject();


        assertThat(article.getName()).isEqualTo("Spring Boot 2");
        assertThat(article.getEan().getValue()).isEqualTo("9783864905254");
    }
}
