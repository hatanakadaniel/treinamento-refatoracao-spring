package com.hatanaka.book.api.service;

import com.hatanaka.book.api.entity.Book;
import com.hatanaka.book.api.resource.book.BookRequest;

import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> findAll();

    Optional<Book> findByName(final String bookName);

    Book create(final BookRequest bookRequest);
}
