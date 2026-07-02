package com.hatanaka.book.api.service;

import com.hatanaka.book.api.domain.BookAlreadyExistsException;
import com.hatanaka.book.api.entity.Book;
import com.hatanaka.book.api.repository.BookRepository;
import com.hatanaka.book.api.resource.request.BookRequest;
import com.hatanaka.book.api.resource.response.BookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public List<BookResponse> listAllBooks() {
        log.info("m=listAllBooks");
        return bookRepository.findAll().stream()
                .map(this::convertTo)
                .collect(Collectors.toList());
    }

    public Optional<BookResponse> findBook(final String bookName) {
        log.info("m=findBook, bookName={}", bookName);
        return bookRepository.findByName(bookName).map(this::convertTo);
    }

    public BookResponse createBook(final BookRequest bookRequest) {
        log.info("m=createBook, bookRequest={}", bookRequest);
        bookRepository.findByName(bookRequest.getName()).ifPresent(existing -> {
            throw BookAlreadyExistsException.builder()
                    .field("name")
                    .message("book already exists")
                    .httpStatus(HttpStatus.CONFLICT)
                    .build();
        });
        final Book book = Optional.of(bookRequest).map(this::convertFrom).orElseThrow();
        return Optional.of(bookRepository.saveAndFlush(book)).map(this::convertTo).orElseThrow();
    }

    private BookResponse convertTo(final Book book) {
        return BookResponse.builder()
                .name(book.getName())
                .build();
    }

    private Book convertFrom(final BookRequest bookRequest) {
        return Book.builder()
                .name(bookRequest.getName())
                .build();
    }
}
