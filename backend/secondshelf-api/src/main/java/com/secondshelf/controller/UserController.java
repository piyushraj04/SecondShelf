package com.secondshelf.controller;

import com.secondshelf.dto.ResponseStructure;
import com.secondshelf.dto.SellerResponseDTO;
import com.secondshelf.dto.UserRequestDTO;
import com.secondshelf.dto.UserResponseDTO;
import com.secondshelf.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class UserController {

    private final UserService userService;


    // =========================================================
    //                    USER REGISTRATION
    // =========================================================

    /*
     * Register a single normal user.
     *
     * POST /api/users/register
     *
     * Normal users are created with BUYER role by default.
     */
    @PostMapping("users/register")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> registerUser(
            @Valid @RequestBody UserRequestDTO userRequestDTO) {

        UserResponseDTO savedUser =
                userService.registerUser(userRequestDTO);

        ResponseStructure<UserResponseDTO> response =
                new ResponseStructure<>();

        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("User Registered Successfully");
        response.setData(savedUser);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }


    /*
     * Register multiple normal users at once.
     *
     * POST /api/users/register-all-users
     *
     * Useful for development/testing and bulk test-data creation.
     *
     * Each user's password is BCrypt-encoded inside UserService
     * before being stored in the database.
     */
    @PostMapping("users/register-all-users")
    public ResponseEntity<ResponseStructure<List<UserResponseDTO>>> registerAllUsers(
            @Valid @RequestBody List<UserRequestDTO> requestDTOS) {

        List<UserResponseDTO> userResponseDTOS =
                userService.registerAllUsers(requestDTOS);

        ResponseStructure<List<UserResponseDTO>> response =
                new ResponseStructure<>();

        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Users Saved Successfully");
        response.setData(userResponseDTOS);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }


    // =========================================================
    //                    SELLER REGISTRATION
    // =========================================================

    /*
     * Register a single seller.
     *
     * POST /api/sellers/register
     *
     * UserService explicitly assigns Role.SELLER.
     */
    @PostMapping("sellers/register")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> registerSeller(
            @Valid @RequestBody UserRequestDTO userRequestDTO) {

        UserResponseDTO savedSeller =
                userService.registerSeller(userRequestDTO);

        ResponseStructure<UserResponseDTO> response =
                new ResponseStructure<>();

        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Seller Registered Successfully");
        response.setData(savedSeller);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }


    /*
     * Register multiple sellers at once.
     *
     * POST /api/sellers/register-all-sellers
     *
     * Useful for development/testing and bulk test-data creation.
     *
     * IMPORTANT:
     * The seller role must be assigned by the service layer.
     * The client should NOT send the role in the request.
     */
    @PostMapping("sellers/register-all-sellers")
    public ResponseEntity<ResponseStructure<List<UserResponseDTO>>> registerAllSellers(
            @Valid @RequestBody List<UserRequestDTO> requestDTOS) {

        List<UserResponseDTO> sellerResponseDTOS =
                userService.registerAllSellers(requestDTOS);

        ResponseStructure<List<UserResponseDTO>> response =
                new ResponseStructure<>();

        response.setStatusCode(HttpStatus.CREATED.value());
        response.setMessage("Sellers Saved Successfully");
        response.setData(sellerResponseDTOS);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }


    // =========================================================
    //                       USER APIs
    // =========================================================

    /*
     * Fetch a user by ID.
     *
     * GET /api/users/{userId}
     */
    @GetMapping("users/{userId}")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> getUserById(
            @PathVariable(name = "userId") Long userId) {

        UserResponseDTO user =
                userService.getUserById(userId);

        ResponseStructure<UserResponseDTO> response =
                new ResponseStructure<>();

        response.setStatusCode(HttpStatus.FOUND.value());
        response.setMessage("User is found successfully");
        response.setData(user);

        return new ResponseEntity<>(
                response,
                HttpStatus.FOUND
        );
    }


    // =========================================================
    //                      SELLER APIs
    // =========================================================

    /*
     * Fetch seller details along with the seller's listings.
     *
     * GET /api/sellers/{sellerId}
     */
    @GetMapping("sellers/{sellerId}")
    public ResponseEntity<ResponseStructure<SellerResponseDTO>> getSellerById(
            @PathVariable(name = "sellerId") Long sellerId) {

        SellerResponseDTO seller =
                userService.getSellerById(sellerId);

        ResponseStructure<SellerResponseDTO> response =
                new ResponseStructure<>();

        response.setStatusCode(HttpStatus.FOUND.value());
        response.setMessage("Seller is found successfully");
        response.setData(seller);

        return new ResponseEntity<>(
                response,
                HttpStatus.FOUND
        );
    }
}