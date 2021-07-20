package com.hatanaka.book.api.service;

import com.hatanaka.book.api.converter.BookConverter;
import com.hatanaka.book.api.entity.Book;
import com.hatanaka.book.api.exception.BookAlreadyExistsException;
import com.hatanaka.book.api.repository.BookRepository;
import com.hatanaka.book.api.resource.book.BookRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookConverter bookConverter;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findByName(final String bookName) {
        return bookRepository.findByName(bookName);
    }

    @Override
    public Book create(final BookRequest bookRequest) {
        bookRepository.findByName(bookRequest.getName()).ifPresent(book1 -> {
            throw BookAlreadyExistsException.builder()
                    .field("name")
                    .message("book already exists")
                    .httpStatus(HttpStatus.CONFLICT)
                    .build();
        });
        final Book book = Optional.of(bookRequest).map(bookConverter::convertFrom).orElseThrow();
        return bookRepository.saveAndFlush(book);
    }
}
