package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SpyTest {

    @Test
    void shouldUseSpyOnList() {
        // Создаем реальный список
        List<String> realList = new ArrayList<>();

        // Создаем spy-объект
        List<String> spyList = spy(realList);

        // Используем реальную логику
        spyList.add("Spring");
        spyList.add("Boot");

        // Проверяем, что add был вызван
        verify(spyList).add("Spring");
        verify(spyList).add("Boot");

        // Проверяем состояние списка
        assertEquals(2, spyList.size());
        assertEquals("Spring", spyList.get(0));
        assertEquals("Boot", spyList.get(1));
    }

    @Test
    void shouldStubSpyMethod() {
        List<String> spyList = spy(new ArrayList<>());

        // Переопределяем поведение метода size()
        when(spyList.size()).thenReturn(100);

        spyList.add("Test");

        assertEquals(100, spyList.size()); // Возвращает stub-значение
        verify(spyList).add("Test");
    }
}