package com.hatanaka.book.api.converter;

import com.hatanaka.book.api.entity.Book;
import com.hatanaka.book.api.resource.book.BookRequest;
import org.springframework.stereotype.Component;

@Component
public class BookConverter {

    public Book convertFrom(final BookRequest bookRequest) {
        return Book.builder()
                .name(bookRequest.getName())
                .build();
    }
}
