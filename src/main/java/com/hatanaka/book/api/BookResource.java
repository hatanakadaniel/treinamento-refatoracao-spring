package com.hatanaka.book.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@Validated
@RequiredArgsConstructor
@Slf4j
public class BookResource {

    private final BookRepository bookRepository;

    @GetMapping("/")
    public ResponseEntity<List<BookResponse>> listAllBooks() {
        log.info("m=listAllBooks");
        final List<Book> books = bookRepository.findAll();
        return ResponseEntity.ok(books.stream()
                                         .map(this::convertTo)
                                         .collect(Collectors.toList()));
    }

    @GetMapping("/{bookName}")
    public ResponseEntity<BookResponse> findBook(@Size(min = 5, max = 10) @PathVariable final String bookName) {
        log.info("m=findBook, bookName={}", bookName);
        return ResponseEntity.of(bookRepository.findByName(bookName).map(this::convertTo));
    }

    @PostMapping("/")
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody final BookRequest bookRequest) {
        log.info("m=createBook, bookRequest={}", bookRequest);
        bookRepository.findByName(bookRequest.getName()).ifPresent(book1 -> {
            throw BookAlreadyExistsException.builder()
                    .field("name")
                    .message("book already exists")
                    .httpStatus(HttpStatus.CONFLICT)
                    .build();
        });
        final Book book = Optional.of(bookRequest).map(this::convertFrom).orElseThrow();
        return ResponseEntity.status(HttpStatus.CREATED).body(Optional.of(bookRepository.saveAndFlush(book)).map(this::convertTo).orElseThrow());
    }

    private BookResponse convertTo(Book book) {
        return BookResponse.builder()
                .name(book.getName())
                .build();
    }

    private Book convertFrom(BookRequest bookRequest) {
        return Book.builder()
                .name(bookRequest.getName())
                .build();
    }
}
