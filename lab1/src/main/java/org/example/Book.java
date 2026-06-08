package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Book(
        @JsonProperty("title") String title,
        @JsonProperty("author") String author,
        @JsonProperty("year") int year
) {
    @Override
    public String toString() {
        return title + " - " + author + " (" + year + ")";
    }
}
