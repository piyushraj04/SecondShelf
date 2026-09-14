package com.secondshelf.service;

import com.secondshelf.dto.BookListingRequestDTO;
import com.secondshelf.dto.BookListingResponseDTO;
import com.secondshelf.dto.SellerResponseDTO;
import com.secondshelf.entity.Book;
import com.secondshelf.entity.BookListing;
import com.secondshelf.entity.User;
import com.secondshelf.enums.Role;
import com.secondshelf.enums.UserStatus;
import com.secondshelf.exception.ForbiddenOperationException;
import com.secondshelf.exception.InactiveUserException;
import com.secondshelf.exception.NotFoundException;
import com.secondshelf.repository.BookListingRepository;
import com.secondshelf.repository.BookRepository;
import com.secondshelf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookListingService {

    private final BookListingRepository bookListingRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;


    /*
     * ============================================================
     * VERIFY SELLER
     * ============================================================
     *
     * Finds the user by sellerId and verifies that:
     *
     * 1. The user exists.
     * 2. The user's account is ACTIVE.
     * 3. The user's role is SELLER.
     *
     * This method is used before performing seller-specific
     * operations such as creating a listing.
     */
    private User getVerifiedSeller(Long sellerId) {

        User seller = userRepository.findById(sellerId)
                .orElseThrow(() ->
                        new NotFoundException("No seller found with this id"));

        // Seller must have an active account.
        if (seller.getUserStatus() != UserStatus.ACTIVE) {
            throw new InactiveUserException("User account is inactive");
        }

        // Only users having SELLER role can perform seller operations.
        if (seller.getRole() != Role.SELLER) {
            throw new ForbiddenOperationException(
                    "You are not allowed to perform this operation"
            );
        }

        return seller;
    }


    /*
     * ============================================================
     * GET BOOK
     * ============================================================
     *
     * Finds the Book entity using the bookId received from the
     * request DTO.
     *
     * If the book does not exist, the operation cannot continue.
     */
    private Book getBook(Long bookId) {

        return bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new NotFoundException("No book found with this id"));
    }


    /*
     * ============================================================
     * REQUEST DTO -> BOOK LISTING ENTITY
     * ============================================================
     *
     * Converts the data received from the client into a
     * BookListing entity that can be saved in the database.
     *
     * Notice that seller and book are received as objects.
     *
     * They were already fetched and verified in saveBookListing(),
     * so this mapper does NOT perform any database operation.
     */
    private BookListing mapBookListingRequestDTOToBookListingEntity(
            User seller,
            Book book,
            BookListingRequestDTO requestDTO) {

        BookListing bookListing = new BookListing();

        // Set the seller who is creating this listing.
        bookListing.setSeller(seller);

        // Associate the existing Book with this listing.
        bookListing.setBook(book);

        // Copy listing-specific information from the request DTO.
        bookListing.setPrice(requestDTO.getPrice());
        bookListing.setCondition(requestDTO.getCondition());
        bookListing.setQuantity(requestDTO.getQuantity());
        bookListing.setDescription(requestDTO.getDescription());
        bookListing.setListingType(requestDTO.getListingType());

        /*
         * When a listing is created, all listed copies are initially
         * available.
         *
         * Example:
         * quantity = 20
         * availableQuantity = 20
         */
        bookListing.setAvailableQuantity(requestDTO.getQuantity());

        return bookListing;
    }


    /*
     * ============================================================
     * BOOK LISTING ENTITY -> RESPONSE DTO
     * ============================================================
     *
     * Converts a BookListing entity into the response DTO that
     * will be sent back to the client.
     *
     * IMPORTANT:
     * This method is ONLY responsible for mapping data.
     *
     * It does NOT call any repository or perform database queries.
     *
     * The BookListing already contains references to:
     * - Seller
     * - Book
     *
     * Therefore, we simply get them from the entity.
     */
    private BookListingResponseDTO mapBookListingEntityToBookListingResponseDTO(
            BookListing bookListing) {

        User seller = bookListing.getSeller();
        Book book = bookListing.getBook();

        BookListingResponseDTO responseDTO = new BookListingResponseDTO();


        // --------------------------------------------------------
        // Listing Details
        // --------------------------------------------------------

        responseDTO.setListingId(bookListing.getId());
        responseDTO.setPrice(bookListing.getPrice());
        responseDTO.setCondition(bookListing.getCondition());
        responseDTO.setQuantity(bookListing.getQuantity());
        responseDTO.setListingDescription(bookListing.getDescription());
        responseDTO.setStatus(bookListing.getStatus());
        responseDTO.setListingType(bookListing.getListingType());
        responseDTO.setAvailableQuantity(bookListing.getAvailableQuantity());


        // --------------------------------------------------------
        // Book Details
        // --------------------------------------------------------
        //
        // A listing is associated with a Book entity.
        // We include the book information in the response so that
        // the frontend does not need another API call just to
        // display basic book information.
        // --------------------------------------------------------

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


        // --------------------------------------------------------
        // Seller Details
        // --------------------------------------------------------
        //
        // We use a separate SellerResponseDTO instead of exposing
        // the complete User entity.
        //
        // This prevents unnecessary User fields from being exposed
        // in the API response.
        // --------------------------------------------------------

        SellerResponseDTO sellerResponseDTO = new SellerResponseDTO();

        sellerResponseDTO.setId(seller.getId());
        sellerResponseDTO.setName(seller.getFullName());
        sellerResponseDTO.setProfileImageUrl(seller.getProfileImageUrl());
        sellerResponseDTO.setUserStatus(seller.getUserStatus());

        /*
         * Seller rating functionality will be implemented later.
         *
         * For now:
         * - averageRating = null
         * - reviewCount = 0
         */
        sellerResponseDTO.setAverageRating(null);
        sellerResponseDTO.setReviewCount(0);

        responseDTO.setSeller(sellerResponseDTO);

        return responseDTO;
    }


    /*
     * ============================================================
     * CREATE BOOK LISTING
     * ============================================================
     *
     * Flow:
     *
     * sellerId
     *    ↓
     * Verify seller
     *    ↓
     * Find requested book
     *    ↓
     * Convert request DTO -> BookListing entity
     *    ↓
     * Save entity in database
     *    ↓
     * Convert saved entity -> Response DTO
     *    ↓
     * Return response
     */
    public BookListingResponseDTO saveBookListing(
            Long sellerId,
            BookListingRequestDTO requestDTO) {

        // Step 1: Find and verify that the user is an active seller.
        User seller = getVerifiedSeller(sellerId);

        // Step 2: Find the book selected by the seller.
        Book book = getBook(requestDTO.getBookId());

        /*
         * Step 3: Convert the request DTO into a BookListing entity.
         *
         * We pass the already-fetched seller and book objects.
         * Therefore, the mapper does not need to query the database.
         */
        BookListing bookListing =
                mapBookListingRequestDTOToBookListingEntity(
                        seller,
                        book,
                        requestDTO
                );

        // Step 4: Save the listing to the database.
        BookListing savedBookListing =
                bookListingRepository.save(bookListing);

        // Step 5: Convert the saved entity into the API response DTO.
        BookListingResponseDTO responseDTO =
                mapBookListingEntityToBookListingResponseDTO(
                        savedBookListing
                );

        return responseDTO;
    }


    /*
     * ============================================================
     * GET ALL BOOK LISTINGS
     * ============================================================
     *
     * Retrieves all listings from the database and converts each
     * BookListing entity into a BookListingResponseDTO.
     *
     * NOTE:
     * For now, this returns all listings.
     *
     * Later, marketplace visibility rules will be handled at the
     * repository/query level.
     *
     * For example:
     * - Listings of inactive sellers should NOT appear.
     * - Inactive listings should NOT appear.
     * - Pagination/filtering/sorting can be added later.
     */
    public List<BookListingResponseDTO> getAllBookListings() {

        // Get all BookListing entities from the database.
        List<BookListing> allBookListings =
                bookListingRepository.findAll();

        // This list will contain the DTOs returned to the client.
        List<BookListingResponseDTO> responseDTOs =
                new ArrayList<>();

        // Convert every entity into its corresponding response DTO.
        for (BookListing listing : allBookListings) {

            BookListingResponseDTO singleResponseDTO =
                    mapBookListingEntityToBookListingResponseDTO(listing);

            responseDTOs.add(singleResponseDTO);
        }

        return responseDTOs;
    }
}