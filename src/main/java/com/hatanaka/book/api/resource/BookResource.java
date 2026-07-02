package com.hatanaka.book.api.resource;

import com.hatanaka.book.api.resource.request.BookRequest;
import com.hatanaka.book.api.resource.response.BookResponse;
import com.hatanaka.book.api.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Validated
@RequiredArgsConstructor
@Slf4j
public class BookResource {

    private final BookService bookService;

    @GetMapping("/")
    public ResponseEntity<List<BookResponse>> listAllBooks() {
        return ResponseEntity.ok(bookService.listAllBooks());
    }

    @GetMapping("/{bookName}")
    public ResponseEntity<BookResponse> findBook(@Size(min = 5, max = 10) @PathVariable final String bookName) {
        return ResponseEntity.of(bookService.findBook(bookName));
    }

    @PostMapping("/")
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody final BookRequest bookRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(bookRequest));
    }
}
