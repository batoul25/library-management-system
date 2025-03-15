package com.library.controllers;

import com.library.entities.BorrowingRecord;
import com.library.services.BorrowingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/borrow")
public class BorrowingController {

    @Autowired
    private BorrowingService borrowingService;

    // Borrow a book
    @PostMapping("/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId,
            @Valid @RequestBody BorrowingRecord borrowingRecord) {
        BorrowingRecord record = borrowingService.borrowBook(bookId, patronId, borrowingRecord);
        return ResponseEntity.ok(record);
    }

    // Return a book
    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBook(
            @PathVariable Long bookId,
            @PathVariable Long patronId,
            @Valid @RequestBody BorrowingRecord borrowingRecord) {
        BorrowingRecord record = borrowingService.returnBook(bookId, patronId, borrowingRecord);
        return ResponseEntity.ok(record);
    }
}