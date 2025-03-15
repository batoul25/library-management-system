package com.library.services;

import com.library.entities.Book;
import com.library.entities.BorrowingRecord;
import com.library.entities.Patron;
import com.library.repositories.BookRepository;
import com.library.repositories.BorrowingRecordRepository;
import com.library.repositories.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingService {

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    // Cache the result of borrowBook() with the book ID and patron ID as the key
    @Cacheable(value = "borrowingRecord", key = "{#bookId, #patronId}")
    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) {
        // Find the book and patron
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new RuntimeException("Patron not found with id: " + patronId));

        // Check if the book is already borrowed
        Optional<BorrowingRecord> existingRecord = borrowingRecordRepository.findByBookAndReturnDateIsNull(book);
        if (existingRecord.isPresent()) {
            throw new RuntimeException("Book is already borrowed");
        }

        // Create a new borrowing record
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(LocalDate.now());
        return borrowingRecordRepository.save(borrowingRecord);
    }

    // Update the cache when a book is returned
    @CachePut(value = "borrowingRecord", key = "{#bookId, #patronId}")
    @Transactional
    public BorrowingRecord returnBook(Long bookId, Long patronId) {
        // Find the book and patron
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookId));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new RuntimeException("Patron not found with id: " + patronId));

        // Find the active borrowing record
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookAndPatronAndReturnDateIsNull(book, patron)
                .orElseThrow(() -> new RuntimeException("No active borrowing record found for this book and patron"));

        // Update the return date
        borrowingRecord.setReturnDate(LocalDate.now());
        return borrowingRecordRepository.save(borrowingRecord);
    }

    // Evict the cache when a borrowing record is deleted 
    @CacheEvict(value = "borrowingRecord", key = "{#bookId, #patronId}")
    public void deleteBorrowingRecord(Long bookId, Long patronId) {
        
    }
}