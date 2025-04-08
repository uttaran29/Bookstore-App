package com.zynetic.bookstore.BookstoreApplication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@With
public class Book {

    @Id
    private UUID id;

    private String title;

    private String author;

    private double price;

    private double rating;

    private LocalDate publishDate;

    private String category;
}
