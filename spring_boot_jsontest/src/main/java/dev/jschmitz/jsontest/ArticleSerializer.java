package dev.jschmitz.jsontest;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class ArticleSerializer extends JsonSerializer<Article> {

    @Override
    public void serialize(Article article, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("name", article.getName());
        jsonGenerator.writeStringField("ean", article.getEan().value());
        jsonGenerator.writeEndObject();
    }
}
