package com.secondshelf.service;

import com.secondshelf.dto.BookListingRequestDTO;
import com.secondshelf.dto.BookListingResponseDTO;
import com.secondshelf.dto.SellerResponseDTO;
import com.secondshelf.entity.Book;
import com.secondshelf.entity.BookListing;
import com.secondshelf.entity.User;
import com.secondshelf.exception.NotFoundException;
import com.secondshelf.repository.BookListingRepository;
import com.secondshelf.repository.BookRepository;
import com.secondshelf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookListingService {

    private final BookListingRepository bookListingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public BookListingResponseDTO saveBookListing(Long sellerId, BookListingRequestDTO requestDTO) {

        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("No seller found with this id"));

        Book book = bookRepository.findById(requestDTO.getBookId())
                .orElseThrow(() -> new NotFoundException("No book found with this id"));

        BookListing bookListing = new BookListing();

        bookListing.setSeller(seller);
        bookListing.setBook(book);
        bookListing.setPrice(requestDTO.getPrice());
        bookListing.setCondition(requestDTO.getCondition());
        bookListing.setQuantity(requestDTO.getQuantity());
        bookListing.setDescription(requestDTO.getDescription());
        bookListing.setListingType(requestDTO.getListingType());
        bookListing.setAvailableQuantity(requestDTO.getQuantity());

        //save to db
        BookListing savedBookListing = bookListingRepository.save(bookListing);

        BookListingResponseDTO responseDTO = new BookListingResponseDTO();

        // Listing details
        responseDTO.setListingId(savedBookListing.getId());
        responseDTO.setPrice(savedBookListing.getPrice());
        responseDTO.setCondition(savedBookListing.getCondition());
        responseDTO.setQuantity(savedBookListing.getQuantity());
        responseDTO.setListingDescription(savedBookListing.getDescription());
        responseDTO.setStatus(savedBookListing.getStatus());
        responseDTO.setListingType(savedBookListing.getListingType());
        responseDTO.setAvailableQuantity(savedBookListing.getAvailableQuantity());

        // Book details
        responseDTO.setBookId(book.getId());
        responseDTO.setTitle(book.getTitle());
        responseDTO.setAuthor(book.getAuthor());
        responseDTO.setIsbn(book.getIsbn());
        responseDTO.setPublisher(book.getPublisher());
        responseDTO.setLanguage(book.getLanguage());
        responseDTO.setPublicationYear(book.getPublicationYear());
        responseDTO.setEdition(book.getEdition());
        responseDTO.setBookDescription(book.getDescription());
        responseDTO.setCategory(book.getCategory());
        responseDTO.setCoverImageUrl(book.getCoverImageUrl());

        // =========================
        // Build SellerResponseDTO
        // =========================
        SellerResponseDTO sellerResponseDTO = new SellerResponseDTO();
        sellerResponseDTO.setId(seller.getId());
        sellerResponseDTO.setName(seller.getFullName());
        sellerResponseDTO.setProfileImageUrl(seller.getProfileImageUrl());
        sellerResponseDTO.setUserStatus(seller.getUserStatus());

        // Rating-related fields will be implemented later
        sellerResponseDTO.setAverageRating(null);
        sellerResponseDTO.setReviewCount(0);

        responseDTO.setSeller(sellerResponseDTO);

        return responseDTO;

    }

}
