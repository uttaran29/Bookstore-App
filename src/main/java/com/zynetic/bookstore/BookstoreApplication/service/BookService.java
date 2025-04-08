package com.zynetic.bookstore.BookstoreApplication.service;

import com.zynetic.bookstore.BookstoreApplication.entity.Book;
import com.zynetic.bookstore.BookstoreApplication.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks(String author, String category, Double rating, String title,
                                  int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Specification<Book> spec = Specification.where(null);

        if (author != null)
            spec = spec.and((root, query, cb) -> cb.equal(root.get("author"), author));

        if (category != null)
            spec = spec.and((root, query, cb) -> cb.equal(root.get("category"), category));

        if (rating != null)
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("rating"), rating));

        if (title != null)
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));

        return bookRepository.findAll(spec, pageable).getContent();
    }

    public Book getBookById(UUID id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Book not found with ID: " + id));
    }

    public Book updateBook(UUID id, Book updatedBook) {
        Book book = getBookById(id);
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setCategory(updatedBook.getCategory());
        book.setPrice(updatedBook.getPrice());
        book.setRating(updatedBook.getRating());
        book.setPublishDate(updatedBook.getPublishDate());
        return bookRepository.save(book);
    }

    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }
}
