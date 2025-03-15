// package com.library.services;

// import com.library.entities.Book;
// import com.library.repositories.BookRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.cache.annotation.CacheEvict;
// import org.springframework.cache.annotation.Cacheable;
// import org.springframework.cache.annotation.CachePut;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class BookService {

//     @Autowired
//     private BookRepository bookRepository;

//     // Cache the result of getAllBooks()
//     @Cacheable("books")
//     public List<Book> getAllBooks() {
//         return bookRepository.findAll();
//     }

//     // Cache the result of getBookById() with the book ID as the key
//     @Cacheable(value = "book", key = "#id")
//     @Transactional(readOnly = true) // Read-only transaction for fetching data
//     public Book getBookById(Long id) {
//         return bookRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Book not found"));
//     }

//     // Update the cache when a new book is added
//     @CachePut(value = "book", key = "#book.id")
//     @Transactional // Writable transaction for adding data
//     public Book addBook(Book book) {
//         return bookRepository.save(book);
//     }

//     // Update the cache when a book is updated
//     @CachePut(value = "book", key = "#id")
//     @Transactional // Writable transaction for updating data
//     public Book updateBook(Long id, Book bookDetails) {
//         Book book = getBookById(id);
//         book.setTitle(bookDetails.getTitle());
//         book.setAuthor(bookDetails.getAuthor());
//         book.setIsbn(bookDetails.getIsbn());
//         book.setPublicationYear(bookDetails.getPublicationYear());
//         return bookRepository.save(book);
//     }

//     // Evict the cache when a book is deleted
//     @CacheEvict(value = "book", key = "#id")
//     @Transactional // Writable transaction for deleting data
//     public void deleteBook(Long id) {
//         bookRepository.deleteById(id);
//     }
// }

package com.library.services;

import com.library.entities.Book;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookService {

    @Transactional(readOnly = true)
    List<Book> getAllBooks();

    @Transactional(readOnly = true)
    Book getBookById(Long id);

    @Transactional
    Book addBook(Book book);

    @Transactional
    Book updateBook(Long id, Book bookDetails);

    @Transactional
    void deleteBook(Long id);
}