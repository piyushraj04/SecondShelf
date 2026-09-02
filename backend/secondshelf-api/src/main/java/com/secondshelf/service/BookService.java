package com.secondshelf.service;

import com.secondshelf.dto.BookRequestDTO;
import com.secondshelf.dto.BookResponseDTO;
import com.secondshelf.entity.Book;
import com.secondshelf.exception.NotFoundException;
import com.secondshelf.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    private Book mapToEntity(BookRequestDTO bookRequestDTO){
        Book book = new Book();

        book.setTitle(bookRequestDTO.getTitle());
        book.setAuthor(bookRequestDTO.getAuthor());
        book.setIsbn(bookRequestDTO.getIsbn());
        book.setCategory(bookRequestDTO.getCategory());
        book.setEdition(bookRequestDTO.getEdition());
        book.setPublicationYear(bookRequestDTO.getPublicationYear());
        book.setLanguage(bookRequestDTO.getLanguage());
        book.setDescription(bookRequestDTO.getDescription());
        book.setPublisher(bookRequestDTO.getPublisher());
        return book;
    }
    private BookResponseDTO mapToResponse(Book savedBook){
        BookResponseDTO bookResponseDTO = new BookResponseDTO();

        bookResponseDTO.setAuthor(savedBook.getAuthor());
        bookResponseDTO.setId(savedBook.getId());
        bookResponseDTO.setCategory(savedBook.getCategory());
        bookResponseDTO.setEdition(savedBook.getEdition());
        bookResponseDTO.setDescription(savedBook.getDescription());
        bookResponseDTO.setIsbn(savedBook.getIsbn());
        bookResponseDTO.setPublisher(savedBook.getPublisher());
        bookResponseDTO.setPublicationYear(savedBook.getPublicationYear());
        bookResponseDTO.setCoverImageUrl(savedBook.getCoverImageUrl());
        bookResponseDTO.setTitle(savedBook.getTitle());
        bookResponseDTO.setLanguage(savedBook.getLanguage());

        return bookResponseDTO;
    }
    public BookResponseDTO saveBook(BookRequestDTO bookRequestDTO){
        Book book = mapToEntity(bookRequestDTO);

        Book savedBook = bookRepository.save(book);

        BookResponseDTO bookResponseDTO = mapToResponse(savedBook);

        return bookResponseDTO;
    }

    public List<BookResponseDTO> saveAllBooks(List<BookRequestDTO> bookRequestDTOS){
        List<Book> books = new ArrayList<>();
        List<BookResponseDTO> bookResponseDTOS = new ArrayList<>();

        for(BookRequestDTO bookRequestDTO : bookRequestDTOS){
            Book book = mapToEntity(bookRequestDTO);

            books.add(book);
        }

        List<Book> savedBooks = bookRepository.saveAll(books);

        for(Book book : savedBooks){
            BookResponseDTO bookResponseDTO = mapToResponse(book);

            bookResponseDTOS.add(bookResponseDTO);

        }
        return bookResponseDTOS;
    }

    public BookResponseDTO getBookById(Long bookId){
        Book fetchedBook = bookRepository.findById(bookId)
                .orElseThrow(()-> new NotFoundException("No book present with this given id"));

        BookResponseDTO bookResponseDTO = mapToResponse(fetchedBook);

        return bookResponseDTO;
    }

    public List<BookResponseDTO> getAllBooks(){
        List<BookResponseDTO> bookResponseDTOS = new ArrayList<>();
        List<Book> fetchedbooks = bookRepository.findAll();
        for(Book book : fetchedbooks){
            BookResponseDTO bookResponseDTO = mapToResponse(book);
            bookResponseDTOS.add(bookResponseDTO);
        }
        return bookResponseDTOS;
    }

}
