package com.library.services;

import com.library.entities.Book;
import com.library.entities.BorrowingRecord;
import com.library.entities.Patron;
import com.library.repositories.BookRepository;
import com.library.repositories.BorrowingRecordRepository;
import com.library.repositories.PatronRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowingServiceTest {

    @Mock
    private BorrowingRecordRepository borrowingRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PatronRepository patronRepository;

    @InjectMocks
    private BorrowingService borrowingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void borrowBook() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book 1");

        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("Patron 1");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.findByBookAndReturnDateIsNull(book)).thenReturn(Optional.empty());

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(LocalDate.now());

        when(borrowingRecordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);

        // Act
        BorrowingRecord result = borrowingService.borrowBook(1L, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(book, result.getBook());
        assertEquals(patron, result.getPatron());
        verify(bookRepository, times(1)).findById(1L);
        verify(patronRepository, times(1)).findById(1L);
        verify(borrowingRecordRepository, times(1)).findByBookAndReturnDateIsNull(book);
        verify(borrowingRecordRepository, times(1)).save(any(BorrowingRecord.class));
    }

    @Test
    void returnBook() {
        // Arrange
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book 1");

        Patron patron = new Patron();
        patron.setId(1L);
        patron.setName("Patron 1");

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(LocalDate.now());

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(patronRepository.findById(1L)).thenReturn(Optional.of(patron));
        when(borrowingRecordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron))
                .thenReturn(Optional.of(borrowingRecord));

        when(borrowingRecordRepository.save(borrowingRecord)).thenReturn(borrowingRecord);

        // Act
        BorrowingRecord result = borrowingService.returnBook(1L, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(LocalDate.now(), result.getReturnDate());
        verify(bookRepository, times(1)).findById(1L);
        verify(patronRepository, times(1)).findById(1L);
        verify(borrowingRecordRepository, times(1)).findByBookAndPatronAndReturnDateIsNull(book, patron);
        verify(borrowingRecordRepository, times(1)).save(borrowingRecord);
    }
}