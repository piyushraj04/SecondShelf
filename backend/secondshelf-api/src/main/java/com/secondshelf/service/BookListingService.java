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

    public BookListingResponseDTO getBookListingByid(Long bookListingId){

        BookListing bookListing = bookListingRepository.findById(bookListingId)
                .orElseThrow(()-> new NotFoundException("no book listing available with this id"));
        BookListingResponseDTO responseDTO = mapBookListingEntityToBookListingResponseDTO(bookListing);
        return responseDTO;
    }

    public List<BookListingResponseDTO> getSellerListings(Long sellerId){
        User seller = getVerifiedSeller(sellerId);
        List<BookListingResponseDTO> listingDTOS = new ArrayList<>();
        List<BookListing> listings = bookListingRepository.findBySellerId(sellerId)
                .orElseThrow(()-> new NotFoundException("No listing associated or currently available with respect to this seller"));

        for(BookListing listing : listings){
            BookListingResponseDTO responseDTO = mapBookListingEntityToBookListingResponseDTO(listing);
            listingDTOS.add(responseDTO);
        }
        return listingDTOS;
    }

    /*
     * ============================================================
     * UPDATE BOOK LISTING
     * ============================================================
     *
     * Updates the editable details of an existing BookListing.
     *
     * sellerId and listingId come from the URL.
     * The remaining listing details come from the request body.
     *
     * Before updating, we verify:
     *
     * 1. Seller exists, is ACTIVE and has SELLER role.
     * 2. Listing exists and belongs to this seller.
     * 3. New quantity is not less than current available quantity.
     * 4. Book associated with the listing cannot be changed.
     * 5. Listing type cannot be changed.
     *
     * Only these fields are actually updated:
     * - price
     * - condition
     * - quantity
     * - description
     */
    public BookListingResponseDTO updateListing(
            Long listingId,
            Long sellerId,
            BookListingRequestDTO requestDTO) {

        /*
         * Step 1:
         * Verify that the seller exists, is ACTIVE,
         * and has the SELLER role.
         */
        getVerifiedSeller(sellerId);

        /*
         * Step 2:
         * Find the listing using BOTH listingId and sellerId.
         *
         * This ensures that the listing belongs to the seller
         * who is trying to update it.
         */
        BookListing bookListing =
                bookListingRepository.findByIdAndSellerId(listingId, sellerId)
                        .orElseThrow(() ->
                                new ForbiddenOperationException(
                                        "You are not allowed to update this listing"
                                ));

        /*
         * Step 3:
         * Make sure the new quantity is not less than the
         * currently available quantity.
         *
         * Example:
         *
         * availableQuantity = 15
         *
         * new quantity = 15  -> allowed
         * new quantity = 20  -> allowed
         * new quantity = 10  -> not allowed
         *
         * More advanced inventory validation will be implemented
         * later when Order/Rental functionality is introduced.
         */
        if (bookListing.getAvailableQuantity() < requestDTO.getQuantity()) {
            throw new ForbiddenOperationException(
                    "Quantity must be greater than or equal to the available quantity"
            );
        }

        /*
         * Step 4:
         * The book associated with an existing listing cannot
         * be changed.
         *
         * If the seller wants to list another book,
         * a new listing should be created.
         */
        if (!bookListing.getBook().getId().equals(requestDTO.getBookId())) {
            throw new ForbiddenOperationException(
                    "Can't change the book of an existing listing. " +
                            "Kindly create a new listing for that book"
            );
        }

        /*
         * Step 5:
         * The listing type cannot be changed.
         *
         * SELL remains SELL.
         * RENT remains RENT.
         *
         * If the seller wants the other listing type,
         * a separate listing should be created.
         */
        if (!bookListing.getListingType().equals(requestDTO.getListingType())) {
            throw new ForbiddenOperationException(
                    "Listing type cannot be changed while updating the listing"
            );
        }

        /*
         * Step 6:
         * Update only the fields that are allowed to change.
         *
         * Seller, Book and ListingType remain unchanged.
         * availableQuantity and status are also not modified here.
         */
        bookListing.setPrice(requestDTO.getPrice());
        bookListing.setCondition(requestDTO.getCondition());
        bookListing.setQuantity(requestDTO.getQuantity());
        bookListing.setDescription(requestDTO.getDescription());

        /*
         * Step 7:
         * Save the existing BookListing entity.
         *
         * We are modifying the entity that was fetched from the
         * database rather than creating a new BookListing.
         *
         * Therefore, JPA treats this as an update to the
         * existing listing.
         */
        BookListing updatedListing =
                bookListingRepository.save(bookListing);

        /*
         * Step 8:
         * Convert the updated entity into the response DTO
         * before returning it to the controller.
         */
        return mapBookListingEntityToBookListingResponseDTO(updatedListing);
    }
}