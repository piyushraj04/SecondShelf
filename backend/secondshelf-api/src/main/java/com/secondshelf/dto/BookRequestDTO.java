package com.secondshelf.dto;

import com.secondshelf.enums.Category;
import com.secondshelf.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequestDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String isbn;

    private String publisher;

    private Language language = Language.ENGLISH;

   @NotNull
    private Integer publicationYear;

    private String edition;

    private String description;

    @NotNull
    private Category category;


}
