package com.secondshelf.service;

import com.secondshelf.dto.BookListingSummaryDTO;
import com.secondshelf.dto.ResponseStructure;
import com.secondshelf.dto.SellerResponseDTO;
import com.secondshelf.dto.UserRequestDTO;
import com.secondshelf.dto.UserResponseDTO;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BookListingRepository bookListingRepository;

    /*
     * PasswordEncoder is provided by Spring Security as a Bean
     * from SecurityConfig.
     *
     * It converts raw passwords into BCrypt hashes before
     * storing them in the database.
     */
    private final PasswordEncoder passwordEncoder;


    // =========================================================
    //                    USER REGISTRATION
    // =========================================================

    /*
     * Register a single normal user.
     *
     * The default role of a normal registered user is BUYER.
     *
     * Flow:
     * Request DTO
     *      ↓
     * Validate duplicate email/contact
     *      ↓
     * Create User entity
     *      ↓
     * BCrypt encode password
     *      ↓
     * Save User
     *      ↓
     * Return UserResponseDTO
     */
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {

        // Check whether the email is already registered.
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new ResourceAlreadyExistsException(
                    "Email already registered"
            );
        }

        // Check whether the contact number is already registered.
        if (userRepository.existsByContactNo(userRequestDTO.getContactNo())) {
            throw new ResourceAlreadyExistsException(
                    "Contact Number already registered"
            );
        }


        User user = new User();

        user.setFullName(userRequestDTO.getFullName());
        user.setContactNo(userRequestDTO.getContactNo());
        user.setEmail(userRequestDTO.getEmail());

        /*
         * NEVER store the raw password in the database.
         *
         * Example:
         *
         * Raw password:
         *     "Amit@1234"
         *
         * BCrypt:
         *     "$2a$10$........"
         *
         * Only the BCrypt hash is stored in PostgreSQL.
         */
        user.setPassword(
                passwordEncoder.encode(userRequestDTO.getPassword())
        );


        // User.role is BUYER by default in the User entity.
        User savedUser = userRepository.save(user);


        return mapUserToResponseDTO(savedUser);
    }


    // =========================================================
    //              BULK USER REGISTRATION
    // =========================================================

    /*
     * Register multiple normal users at once.
     *
     * Mainly useful for development/testing and creating
     * multiple test users.
     *
     * Every password is BCrypt-encoded before saveAll().
     */
    public List<UserResponseDTO> registerAllUsers(
            List<UserRequestDTO> requestDTOS) {

        List<User> users = new ArrayList<>();

        for (UserRequestDTO request : requestDTOS) {

            // Check duplicate email.
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new ResourceAlreadyExistsException(
                        "Email already registered: " + request.getEmail()
                );
            }

            // Check duplicate contact number.
            if (userRepository.existsByContactNo(request.getContactNo())) {
                throw new ResourceAlreadyExistsException(
                        "Contact Number already registered: "
                                + request.getContactNo()
                );
            }


            User user = new User();

            user.setFullName(request.getFullName());
            user.setContactNo(request.getContactNo());
            user.setEmail(request.getEmail());

            // Encode password before storing it.
            user.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );

            // BUYER is the default role.
            users.add(user);
        }


        // Save all users in one repository operation.
        List<User> savedUsers = userRepository.saveAll(users);


        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();

        for (User savedUser : savedUsers) {
            userResponseDTOS.add(
                    mapUserToResponseDTO(savedUser)
            );
        }

        return userResponseDTOS;
    }


    // =========================================================
    //                   SELLER REGISTRATION
    // =========================================================

    /*
     * Register a single seller.
     *
     * Unlike normal user registration, the SELLER role
     * is explicitly assigned here.
     */
    public UserResponseDTO registerSeller(
            UserRequestDTO userRequestDTO) {

        // Check whether the email is already registered.
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new ResourceAlreadyExistsException(
                    "Email already registered"
            );
        }

        // Check whether the contact number is already registered.
        if (userRepository.existsByContactNo(userRequestDTO.getContactNo())) {
            throw new ResourceAlreadyExistsException(
                    "Contact Number already registered"
            );
        }


        User seller = new User();

        seller.setFullName(userRequestDTO.getFullName());
        seller.setContactNo(userRequestDTO.getContactNo());
        seller.setEmail(userRequestDTO.getEmail());

        /*
         * Encode the seller password before storing it.
         * Raw passwords must never be stored in the database.
         */
        seller.setPassword(
                passwordEncoder.encode(userRequestDTO.getPassword())
        );

        // Explicitly assign the SELLER role.
        seller.setRole(Role.SELLER);


        User savedSeller = userRepository.save(seller);


        return mapUserToResponseDTO(savedSeller);
    }


    // =========================================================
    //                BULK SELLER REGISTRATION
    // =========================================================

    /*
     * Register multiple sellers at once.
     *
     * Endpoint:
     * POST /api/sellers/register-all-sellers
     *
     * Mainly useful for development/testing.
     *
     * The client does NOT provide the role.
     * This method always assigns Role.SELLER.
     */
    public List<UserResponseDTO> registerAllSellers(
            List<UserRequestDTO> requestDTOS) {

        List<User> sellers = new ArrayList<>();


        for (UserRequestDTO request : requestDTOS) {

            // Check duplicate email.
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new ResourceAlreadyExistsException(
                        "Email already registered: " + request.getEmail()
                );
            }

            // Check duplicate contact number.
            if (userRepository.existsByContactNo(request.getContactNo())) {
                throw new ResourceAlreadyExistsException(
                        "Contact Number already registered: "
                                + request.getContactNo()
                );
            }


            User seller = new User();

            seller.setFullName(request.getFullName());
            seller.setContactNo(request.getContactNo());
            seller.setEmail(request.getEmail());

            // Encode password before saving.
            seller.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );

            // Every user created by this method is a SELLER.
            seller.setRole(Role.SELLER);

            sellers.add(seller);
        }


        // Save all sellers in one repository operation.
        List<User> savedSellers = userRepository.saveAll(sellers);


        List<UserResponseDTO> sellerResponseDTOS = new ArrayList<>();

        for (User savedSeller : savedSellers) {
            sellerResponseDTOS.add(
                    mapUserToResponseDTO(savedSeller)
            );
        }

        return sellerResponseDTOS;
    }


    // =========================================================
    //                     GET USER BY ID
    // =========================================================

    /*
     * Fetch a user by ID.
     */
    public UserResponseDTO getUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new NotFoundException("User not found")
                );

        return mapUserToResponseDTO(user);
    }


    // =========================================================
    //                    GET SELLER BY ID
    // =========================================================

    /*
     * Fetch seller details along with the seller's listings.
     *
     * The seller must:
     * 1. Exist
     * 2. Be ACTIVE
     * 3. Have SELLER role
     */
    public SellerResponseDTO getSellerById(Long sellerId) {

        User seller = userRepository
                .findByIdAndUserStatusAndRole(
                        sellerId,
                        UserStatus.ACTIVE,
                        Role.SELLER
                )
                .orElseThrow(
                        () -> new NotFoundException("Seller not found")
                );


        // Fetch all listings belonging to this seller.
        List<BookListing> listings =
                bookListingRepository.findBySellerId(sellerId);


        /*
         * SellerResponseDTO contains a list of
         * BookListingSummaryDTO.
         *
         * We use the summary DTO here to avoid nesting the
         * complete SellerResponseDTO inside every listing.
         */
        List<BookListingSummaryDTO> listingSummaryDTOS =
                new ArrayList<>();


        for (BookListing listing : listings) {

            BookListingSummaryDTO summaryDTO =
                    new BookListingSummaryDTO();

            Book book = listing.getBook();


            // ---------------- BOOK DETAILS ----------------

            summaryDTO.setBookId(book.getId());
            summaryDTO.setAuthor(book.getAuthor());
            summaryDTO.setTitle(book.getTitle());
            summaryDTO.setIsbn(book.getIsbn());
            summaryDTO.setPublisher(book.getPublisher());
            summaryDTO.setLanguage(book.getLanguage());
            summaryDTO.setPublicationYear(book.getPublicationYear());
            summaryDTO.setBookDescription(book.getDescription());
            summaryDTO.setEdition(book.getEdition());
            summaryDTO.setCategory(book.getCategory());
            summaryDTO.setCoverImageUrl(book.getCoverImageUrl());


            // ---------------- LISTING DETAILS ----------------

            summaryDTO.setListingId(listing.getId());
            summaryDTO.setPrice(listing.getPrice());
            summaryDTO.setCondition(listing.getCondition());
            summaryDTO.setQuantity(listing.getQuantity());
            summaryDTO.setDescription(listing.getDescription());
            summaryDTO.setStatus(listing.getStatus());
            summaryDTO.setListingType(listing.getListingType());
            summaryDTO.setAvailableQuantity(
                    listing.getAvailableQuantity()
            );


            listingSummaryDTOS.add(summaryDTO);
        }


        // ---------------- SELLER RESPONSE ----------------

        SellerResponseDTO sellerResponseDTO =
                new SellerResponseDTO();

        sellerResponseDTO.setId(seller.getId());
        sellerResponseDTO.setName(seller.getFullName());
        sellerResponseDTO.setUserStatus(seller.getUserStatus());
        sellerResponseDTO.setProfileImageUrl(
                seller.getProfileImageUrl()
        );

        // Attach seller's listings.
        sellerResponseDTO.setListings(listingSummaryDTOS);


        /*
         * Rating and review count are intentionally not implemented
         * yet. They will be added when Review functionality is completed.
         */

        return sellerResponseDTO;
    }


    // =========================================================
    //                USER → RESPONSE DTO MAPPER
    // =========================================================

    /*
     * Converts User entity into UserResponseDTO.
     *
     * Password is intentionally NOT copied to the response DTO.
     *
     * Keeping this mapping in one method avoids repeating the
     * same DTO conversion code in multiple registration methods.
     */
    private UserResponseDTO mapUserToResponseDTO(User user) {

        UserResponseDTO responseDTO =
                new UserResponseDTO();

        responseDTO.setId(user.getId());
        responseDTO.setFullName(user.getFullName());
        responseDTO.setContactNo(user.getContactNo());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setRole(user.getRole());
        responseDTO.setUserStatus(user.getUserStatus());
        responseDTO.setProfileImageUrl(user.getProfileImageUrl());

        return responseDTO;
    }
}