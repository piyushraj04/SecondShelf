package com.secondshelf.dto;

import com.secondshelf.enums.Category;
import com.secondshelf.enums.Language;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookResponseDTO {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Category category;
    private Integer publicationYear;
    private String edition;
    private String description;
    private String coverImageUrl;
    private Language language;
}
