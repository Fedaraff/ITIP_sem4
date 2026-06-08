package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class JsonUtils {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .findAndRegisterModules();

    public static void saveBooksToFile(List<Book> books, String filename) {
        try {
            objectMapper.writeValue(Path.of(filename).toFile(), books);
            logger.info("Books saved to file: {}", filename);
        } catch (IOException e) {
            logger.error("Error saving books to file", e);
        }
    }

    public static List<Book> loadBooksFromFile(String filename) {
        try {
            File file = Path.of(filename).toFile();
            if (file.exists()) {
                List<Book> books = objectMapper.readValue(file,
                        new TypeReference<List<Book>>() {});
                logger.info("Books loaded from file: {}", filename);
                return books;
            }
        } catch (IOException e) {
            logger.error("Error loading books from file", e);
        }
        return List.of();
    }
}
