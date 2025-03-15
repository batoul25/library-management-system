package com.library.controllers;

import com.library.entities.Book;
import com.library.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void getAllBooks() throws Exception {
        // Arrange
        Book book1 = new Book();
        book1.setTitle("Book 1");
        Book book2 = new Book();
        book2.setTitle("Book 2");
        List<Book> books = Arrays.asList(book1, book2);

        when(bookService.getAllBooks()).thenReturn(books);

        // Act & Assert
        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[1].title").value("Book 2"));
    }

    @Test
    void getBookById() throws Exception {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book 1");

        when(bookService.getBookById(1L)).thenReturn(book);

        // Act & Assert
        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book 1"));
    }

    @Test
    void addBook() throws Exception {
        // Arrange
        Book book = new Book();
        book.setTitle("New Book");

        when(bookService.addBook(any(Book.class))).thenReturn(book);

        // Act & Assert
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Book\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    void updateBook() throws Exception {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Updated Book");

        when(bookService.updateBook(eq(1L), any(Book.class))).thenReturn(book);

        // Act & Assert
        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Book\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Book"));
    }

    @Test
    void deleteBook() throws Exception {
        // Arrange
        doNothing().when(bookService).deleteBook(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }
}