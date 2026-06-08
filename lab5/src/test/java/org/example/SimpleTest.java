package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleTest {

    @Test
    void shouldAddNumbers() {
        int result = 2 + 3;
        assertEquals(5, result);
    }

    @Test
    void shouldCheckString() {
        String value = "Spring Boot";
        assertNotNull(value);
        assertTrue(value.startsWith("Spring"));
        assertFalse(value.isEmpty());
    }

    @Test
    void shouldThrowException() {
        assertThrows(ArithmeticException.class, () -> {
            int result = 10 / 0;
        });
    }
}