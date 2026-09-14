package com.secondshelf.controller;

import com.secondshelf.dto.BookListingRequestDTO;
import com.secondshelf.dto.BookListingResponseDTO;
import com.secondshelf.dto.ResponseStructure;
import com.secondshelf.repository.BookListingRepository;
import com.secondshelf.service.BookListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sellers/{sellerId}/listings")
public class BookListingController {

    private final BookListingService bookListingService;

    @PostMapping
    public ResponseEntity<ResponseStructure<BookListingResponseDTO>> saveBookListing(@PathVariable Long sellerId, @Valid @RequestBody BookListingRequestDTO requestDTO){
        BookListingResponseDTO SavedResponseDTO = bookListingService.saveBookListing(sellerId,requestDTO);
        ResponseStructure<BookListingResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Book listing successfully created");
        response.setData(SavedResponseDTO);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

}
