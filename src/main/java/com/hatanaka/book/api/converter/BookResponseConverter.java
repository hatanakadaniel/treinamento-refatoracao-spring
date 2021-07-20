package com.hatanaka.book.api.converter;

import com.hatanaka.book.api.entity.Book;
import com.hatanaka.book.api.resource.book.BookResponse;
import org.springframework.stereotype.Component;

@Component
public class BookResponseConverter {

    public BookResponse convertFrom(final Book book) {
        return BookResponse.builder()
                .name(book.getName())
                .build();
    }
}
