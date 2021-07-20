package com.hatanaka.book.api.resource.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest implements Serializable {

    @NotBlank
    @Size(min = 5, max = 10)
    private String name;

}
