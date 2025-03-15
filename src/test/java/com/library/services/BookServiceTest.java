package com.library.services;

import com.library.entities.Book;
import com.library.repositories.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks() {
        // Arrange
        Book book1 = new Book();
        book1.setTitle("Book 1");
        Book book2 = new Book();
        book2.setTitle("Book 2");
        List<Book> books = Arrays.asList(book1, book2);

        when(bookRepository.findAll()).thenReturn(books);

        // Act
        List<Book> result = bookService.getAllBooks();

        // Assert
        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book 1");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        Book result = bookService.getBookById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("Book 1", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void addBook() {
        // Arrange
        Book book = new Book();
        book.setTitle("New Book");

        when(bookRepository.save(book)).thenReturn(book);

        // Act
        Book result = bookService.addBook(book);

        // Assert
        assertNotNull(result);
        assertEquals("New Book", result.getTitle());
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBook() {
        // Arrange
        Book existingBook = new Book();
        existingBook.setId(1L);
        existingBook.setTitle("Old Title");

        Book updatedBook = new Book();
        updatedBook.setTitle("New Title");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        // Act
        Book result = bookService.updateBook(1L, updatedBook);

        // Assert
        assertNotNull(result);
        assertEquals("New Title", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(existingBook);
    }

    @Test
    void deleteBook() {
        // Arrange
        Book book = new Book();
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).deleteById(1L);

        // Act
        bookService.deleteBook(1L);

        // Assert
        verify(bookRepository, times(1)).deleteById(1L);
    }
}