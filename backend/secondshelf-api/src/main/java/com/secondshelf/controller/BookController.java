package com.secondshelf.controller;

import com.secondshelf.dto.BookRequestDTO;
import com.secondshelf.dto.BookResponseDTO;
import com.secondshelf.dto.ResponseStructure;
import com.secondshelf.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<ResponseStructure<BookResponseDTO>> saveBook(@Valid @RequestBody BookRequestDTO bookRequestDTO){
       BookResponseDTO savedBook = bookService.saveBook(bookRequestDTO);
        ResponseStructure<BookResponseDTO> response = new ResponseStructure<>();
        response.setMessage("Book Record Saved Successfully");
        response.setStatusCode(HttpStatus.CREATED.value());
        response.setData(savedBook);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }


    @PostMapping("/all")
    public ResponseEntity<ResponseStructure<List<BookResponseDTO>>> saveAllBooks(@Valid @RequestBody List<BookRequestDTO> bookRequestDTOS){
        List<BookResponseDTO> savedBooks = bookService.saveAllBooks(bookRequestDTOS);
        ResponseStructure<List<BookResponseDTO>> response = new ResponseStructure<>();
        response.setMessage("All books saved succesfully");
        response.setStatusCode((HttpStatus.CREATED.value()));
        response.setData(savedBooks);
        return new ResponseEntity<>(response,HttpStatus.CREATED);

    }

    @GetMapping("/{bookId}")
    public ResponseEntity<ResponseStructure<BookResponseDTO>> getBookById(@PathVariable(name = "bookId") Long bookId){
        BookResponseDTO fetchedBook = bookService.getBookById(bookId);
        ResponseStructure<BookResponseDTO> response = new ResponseStructure<>();
        response.setMessage("Book fetched succeesfully");
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(fetchedBook);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<ResponseStructure<List<BookResponseDTO>>> getAllBooks(){
        List<BookResponseDTO> fetchedBooks = bookService.getAllBooks();
        ResponseStructure<List<BookResponseDTO>> response = new ResponseStructure<>();
        response.setMessage("All book fetched Succeesfully");
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(fetchedBooks);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
