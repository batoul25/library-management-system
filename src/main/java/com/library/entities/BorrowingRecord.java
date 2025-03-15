package com.library.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class BorrowingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Borrowing date is required")
    @PastOrPresent(message = "Borrowing date must be in the past or present")
    private LocalDate borrowingDate;

    @FutureOrPresent(message = "Return date must be in the future or present")
    private LocalDate returnDate;

    @NotNull(message = "Book is required")
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @NotNull(message = "Patron is required")
    @ManyToOne
    @JoinColumn(name = "patron_id")
    private Patron patron;
}