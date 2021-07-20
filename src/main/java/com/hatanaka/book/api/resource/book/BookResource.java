package com.hatanaka.book.api.resource.book;

import com.hatanaka.book.api.converter.BookResponseConverter;
import com.hatanaka.book.api.entity.Book;
import com.hatanaka.book.api.service.BookService;
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

    private final BookService bookService;
    private final BookResponseConverter bookResponseConverter;

    @GetMapping("/")
    public ResponseEntity<List<BookResponse>> listAllBooks() {
        log.info("m=listAllBooks");
        final List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books.stream().map(bookResponseConverter::convertFrom).collect(Collectors.toList()));
    }

    @GetMapping("/{bookName}")
    public ResponseEntity<BookResponse> findBook(@Size(min = 5, max = 10) @PathVariable final String bookName) {
        log.info("m=findBook, bookName={}", bookName);
        return ResponseEntity.of(bookService.findByName(bookName).map(bookResponseConverter::convertFrom));
    }

    @PostMapping("/")
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody final BookRequest bookRequest) {
        log.info("m=createBook, bookRequest={}", bookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(Optional.of(bookService.create(bookRequest))
                                                                      .map(bookResponseConverter::convertFrom)
                                                                      .orElseThrow());
    }

}
