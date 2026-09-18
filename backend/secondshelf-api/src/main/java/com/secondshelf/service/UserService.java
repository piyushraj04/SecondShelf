package com.secondshelf.service;

import com.secondshelf.dto.*;
import com.secondshelf.entity.Book;
import com.secondshelf.entity.BookListing;
import com.secondshelf.entity.User;
import com.secondshelf.enums.Role;
import com.secondshelf.enums.UserStatus;
import com.secondshelf.exception.NotFoundException;
import com.secondshelf.exception.ResourceAlreadyExistsException;
import com.secondshelf.repository.BookListingRepository;
import com.secondshelf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

//    private final BookListingService bookListingService; //only one need thats why create it inside that specific method

    //@RequiredArgsConstructor--The @RequiredArgsConstructor is a Lombok annotation that automatically generates a constructor with one parameter for each final field and each field marked with @NonNull that is not initialized at the declaration site.
//    UserService(UserRepository userRepository){
//        this.userRepository = userRepository;
//    }

    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered");
        }
        if (userRepository.existsByContactNo(userRequestDTO.getContactNo())) {
            throw new ResourceAlreadyExistsException("Contact Number already registered");
        }
        User user = new User();
        user.setFullName(userRequestDTO.getFullName());
        user.setContactNo(userRequestDTO.getContactNo());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());

        User savedUser = userRepository.save(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(savedUser.getId());
        userResponseDTO.setFullName(savedUser.getFullName());
        userResponseDTO.setContactNo(savedUser.getContactNo());
        userResponseDTO.setEmail(savedUser.getEmail());
        userResponseDTO.setRole(savedUser.getRole());
        userResponseDTO.setUserStatus(savedUser.getUserStatus());
        userResponseDTO.setProfileImageUrl(savedUser.getProfileImageUrl());

        return userResponseDTO;
    }

    public UserResponseDTO registerSeller(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("Email already registered");
        }
        if (userRepository.existsByContactNo(userRequestDTO.getContactNo())) {
            throw new ResourceAlreadyExistsException("Contact Number already registered");
        }
        User seller = new User();
        seller.setFullName(userRequestDTO.getFullName());
        seller.setContactNo(userRequestDTO.getContactNo());
        seller.setEmail(userRequestDTO.getEmail());
        seller.setPassword(userRequestDTO.getPassword());
        seller.setRole(Role.SELLER);

        User savedSeller = userRepository.save(seller);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(savedSeller.getId());
        userResponseDTO.setFullName(savedSeller.getFullName());
        userResponseDTO.setContactNo(savedSeller.getContactNo());
        userResponseDTO.setEmail(savedSeller.getEmail());
        userResponseDTO.setRole(savedSeller.getRole());
        userResponseDTO.setUserStatus(savedSeller.getUserStatus());
        userResponseDTO.setProfileImageUrl(savedSeller.getProfileImageUrl());

        return userResponseDTO;
    }

    public UserResponseDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setRole(user.getRole());
        userResponseDTO.setUserStatus(user.getUserStatus());
        userResponseDTO.setContactNo(user.getContactNo());
        userResponseDTO.setFullName(user.getFullName());
        userResponseDTO.setProfileImageUrl(user.getProfileImageUrl());
        return userResponseDTO;
    }



    private final BookListingRepository bookListingRepository;
    public SellerResponseDTO getSellerById(Long sellerId) {
        //no need bcz it already verified at-->bookListingService.getSellerListings(sellerId);
        User seller = userRepository.findByIdAndUserStatusAndRole(sellerId, UserStatus.ACTIVE, Role.SELLER)
                .orElseThrow(() -> new NotFoundException("Seller not found"));

        List<BookListing> listings = bookListingRepository.findBySellerId(sellerId);
        List<BookListingSummaryDTO> listingSummaryDTOS = new ArrayList<>();
        for(BookListing listing : listings){
            BookListingSummaryDTO summaryDTO = new BookListingSummaryDTO();
            Book book = listing.getBook();

            summaryDTO.setBookId(book.getId());
            summaryDTO.setAuthor((book.getAuthor()));
            summaryDTO.setTitle(book.getTitle());
            summaryDTO.setIsbn(book.getIsbn());
            summaryDTO.setPublisher(book.getPublisher());
            summaryDTO.setLanguage(book.getLanguage());
            summaryDTO.setPublicationYear(book.getPublicationYear());
            summaryDTO.setBookDescription(book.getDescription());
            summaryDTO.setEdition(book.getEdition());
            summaryDTO.setCategory(book.getCategory());
            summaryDTO.setCoverImageUrl(book.getCoverImageUrl());

            summaryDTO.setListingId(listing.getId());
            summaryDTO.setPrice(listing.getPrice());
            summaryDTO.setCondition(listing.getCondition());
            summaryDTO.setQuantity(listing.getQuantity());
            summaryDTO.setDescription(listing.getDescription());
            summaryDTO.setStatus(listing.getStatus());
            summaryDTO.setListingType(listing.getListingType());
            summaryDTO.setAvailableQuantity(listing.getAvailableQuantity());
            listingSummaryDTOS.add(summaryDTO);

        }

        SellerResponseDTO sellerResponseDTO = new SellerResponseDTO();

        sellerResponseDTO.setId(seller.getId());
        sellerResponseDTO.setName(seller.getFullName());
        sellerResponseDTO.setUserStatus(seller.getUserStatus());
        sellerResponseDTO.setProfileImageUrl(seller.getProfileImageUrl());
        sellerResponseDTO.setListings(listingSummaryDTOS);

        //neglected rating and review count for now
        return sellerResponseDTO;
    }

}
