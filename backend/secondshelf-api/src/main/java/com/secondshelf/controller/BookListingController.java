package com.secondshelf.controller;

import com.secondshelf.dto.BookListingRequestDTO;
import com.secondshelf.dto.BookListingResponseDTO;
import com.secondshelf.dto.ResponseStructure;
import com.secondshelf.entity.BookListing;
import com.secondshelf.service.BookListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sellers")
public class BookListingController {

    private final BookListingService bookListingService;


    /*
     * ============================================================
     * CREATE BOOK LISTING
     * ============================================================
     *
     * The sellerId comes from the URL.
     *
     * The request body contains the listing details such as:
     * bookId, price, condition, quantity, description, listingType.
     *
     * @Valid triggers validation on BookListingRequestDTO.
     */
    @PostMapping("/{sellerId}/listings")
    public ResponseEntity<ResponseStructure<BookListingResponseDTO>> saveBookListing(
            @PathVariable Long sellerId,
            @Valid @RequestBody BookListingRequestDTO requestDTO) {

        BookListingResponseDTO savedResponseDTO =
                bookListingService.saveBookListing(sellerId, requestDTO);

        ResponseStructure<BookListingResponseDTO> response =
                new ResponseStructure<>();

        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Book listing successfully created");
        response.setData(savedResponseDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    /*
     * ============================================================
     * GET ALL BOOK LISTINGS
     * ============================================================
     *
     * Fetches all book listings from the service and returns them
     * to the client.
     *
     * For now, all listings are returned.
     * Marketplace visibility rules will be added later.
     */
    @GetMapping("/listings")
    public ResponseEntity<ResponseStructure<List<BookListingResponseDTO>>> getAllBookListings() {

        List<BookListingResponseDTO> fetchedBookListings =
                bookListingService.getAllBookListings();

        ResponseStructure<List<BookListingResponseDTO>> response =
                new ResponseStructure<>();

        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("All listings fetched successfully");
        response.setData(fetchedBookListings);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
     * ============================================================
     * GET BOOK LISTINGS BY ID
     * ============================================================
     */

    @GetMapping("/{bookListingId}")
    public ResponseEntity<ResponseStructure<BookListingResponseDTO>> getBookListingByid(@PathVariable(name = "bookListingId") Long bookListingId){
        BookListingResponseDTO responseDTO = bookListingService.getBookListingByid(bookListingId);
        ResponseStructure<BookListingResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Book Listing fetched with the given id");
        response.setData(responseDTO);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{sellerId}/listings")
    public ResponseEntity<ResponseStructure<List<BookListingResponseDTO>>> getSellerListings(@PathVariable(name = "sellerId") Long sellerId){
        List<BookListingResponseDTO> responseDTOS = bookListingService.getSellerListings(sellerId);
        ResponseStructure<List<BookListingResponseDTO>> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("All listing associated with seller has been fetched successfully");
        response.setData(responseDTOS);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/{listingId}/{sellerId}")
    public ResponseEntity<ResponseStructure<BookListingResponseDTO>> updateListing(@PathVariable Long listingId,@PathVariable Long sellerId,@Valid @RequestBody BookListingRequestDTO requestDTO){
        BookListingResponseDTO updatedListing = bookListingService.updateListing(listingId,sellerId,requestDTO);
        ResponseStructure<BookListingResponseDTO> response = new ResponseStructure<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Updatd successfully");
        response.setData(updatedListing);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }



}