package com.hatanaka.book.api;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class BookAlreadyExistsException extends RuntimeException {

    private Throwable cause;
    private String field;
    private String message;
    private HttpStatus httpStatus;


}
