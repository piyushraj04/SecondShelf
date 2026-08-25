package com.secondshelf.service;

import com.secondshelf.dto.AddressRequestDTO;
import com.secondshelf.dto.AddressResponseDTO;
import com.secondshelf.entity.Address;
import com.secondshelf.entity.User;
import com.secondshelf.exception.NotFoundException;
import com.secondshelf.repository.AddressRepository;
import com.secondshelf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressResponseDTO addAddress(Long userId, AddressRequestDTO addressRequestDTO) {
//        Optional<User> opt = userRepository.findById(userId);
//        User user;
//        if (opt.isPresent()) {
//            user = opt.get();
//        } else {
//            throw new NotFoundException("User Not found");
//        }

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("User Not Found"));
        Address address = new Address();
        address.setAddressLine(addressRequestDTO.getAddressLine());
        address.setCity(addressRequestDTO.getCity());
        address.setState(addressRequestDTO.getState());
        address.setPincode(addressRequestDTO.getPincode());
        address.setDefault(addressRequestDTO.isDefault());
        address.setType(addressRequestDTO.getType());
        address.setUser(user);

        Address savedAddress = addressRepository.save(address);

        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();

        addressResponseDTO.setAddressLine(savedAddress.getAddressLine());
        addressResponseDTO.setId(savedAddress.getId());
        addressResponseDTO.setCity(savedAddress.getCity());
        addressResponseDTO.setState(savedAddress.getState());
        addressResponseDTO.setPincode(savedAddress.getPincode());
        addressResponseDTO.setDefault(savedAddress.isDefault());
        addressResponseDTO.setType(savedAddress.getType());
        addressResponseDTO.setUserId(savedAddress.getUser().getId());
        return addressResponseDTO;
    }
}
