package com.hatanaka.book.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "uk_book", columnNames = "nam_name")
})
public class Book {

    @Id
    @Column(name = "idt_book")
    @GeneratedValue
    private Long id;

    @Column(name = "nam_name")
    private String name;
}
