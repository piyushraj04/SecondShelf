package com.secondshelf.entity;

import com.secondshelf.enums.Category;
import com.secondshelf.enums.Language;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false,unique = true)
    private String isbn;


    private String publisher;

    @Enumerated(EnumType.STRING)
    private Language language = Language.ENGLISH;

    @Column(nullable = false)
    private Integer publicationYear;

    private String edition;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    private String coverImageUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "book",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review> reviews;







}
