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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressResponseDTO addAddress(Long userId, AddressRequestDTO addressRequestDTO) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User Not Found"));

        //if address is default
//        if(addressRequestDTO.isDefault()){
////            we're modifying the existing entity, whose other fields are already populated by findByuserIdAndIsDefaultTrue.
//            Optional<Address> currentDefaultAddress = addressRepository.findByuserIdAndIsDefaultTrue(userId);
////            currentDefaultAddress.ifPresent(address -> address.setDefault(false));
//            Address currAddress;
//            if(currentDefaultAddress.isPresent()){
//                currAddress = currentDefaultAddress.get();
//                currAddress.setDefault(false);
//                addressRepository.save(currAddress);
//            }
//        }

        if(addressRequestDTO.isDefault()){
            addressRepository.findByuserIdAndIsDefaultTrue(userId)
                    .ifPresent(address -> {
                        address.setDefault(false);
                        addressRepository.save(address);
                    });
        }
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

    public List<AddressResponseDTO> getAllAddresses(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with this given id is not registered"));

        List<Address> savedAddress = addressRepository.findByUserId(userId);
        List<AddressResponseDTO> listOfAddressResponseDTO = new ArrayList<>();
        for (Address address : savedAddress) {
            AddressResponseDTO addressResponseDTO = new AddressResponseDTO();

            addressResponseDTO.setId(address.getId());
            addressResponseDTO.setAddressLine(address.getAddressLine());
            addressResponseDTO.setState(address.getState());
            addressResponseDTO.setPincode(address.getPincode());
            addressResponseDTO.setCity(address.getCity());
            addressResponseDTO.setDefault(address.isDefault());
            addressResponseDTO.setType(address.getType());
            addressResponseDTO.setUserId(user.getId());

            listOfAddressResponseDTO.add(addressResponseDTO);
        }
        return listOfAddressResponseDTO;
    }

    public AddressResponseDTO getAddressById(Long userId,Long addressId){
        Address address = addressRepository.findByIdAndUserId(addressId,userId)
                .orElseThrow(()-> new NotFoundException("No address found corresponding to this user"));

        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();

        addressResponseDTO.setId(address.getId());
        addressResponseDTO.setAddressLine(address.getAddressLine());
        addressResponseDTO.setState(address.getState());
        addressResponseDTO.setPincode(address.getPincode());
        addressResponseDTO.setCity(address.getCity());
        addressResponseDTO.setDefault(address.isDefault());
        addressResponseDTO.setType(address.getType());
        addressResponseDTO.setUserId(address.getUser().getId());

        return addressResponseDTO;
    }

    public AddressResponseDTO updateAddress(Long userId,Long addressId, AddressRequestDTO addressRequestDTO){


        if(!userRepository.existsById(userId)){
            throw new NotFoundException("User not found with given id");
        }

        //don't necessarily need existsById(addressId), findByIdAndUserId already tells us.
//        if(!addressRepository.existsById(addressId)) throw new NotFoundException("Address id is not present in the db");
        Address address = addressRepository.findByIdAndUserId(addressId,userId)
                .orElseThrow(()-> new NotFoundException("No address found corresponding to this user"));
//        If an address is being created/updated as default, find the user's existing default address and unset it before setting the new/current address as default.
        if(addressRequestDTO.isDefault()){
            addressRepository.findByuserIdAndIsDefaultTrue(userId)
                    .ifPresent(currDefault -> {
                       if(!currDefault.getId().equals(address.getId())) {
                           currDefault.setDefault(false);
                           addressRepository.save(currDefault);
                       }
                    });
        }

//        address.setUser(address.getUser());
//        address.setId(addressId);
//        -We're already updating the existing address entity by Address address = addressRepository.findByIdAndUserId(...);Therefore it already has:id and user

        address.setType(addressRequestDTO.getType());
        address.setAddressLine(addressRequestDTO.getAddressLine());
        address.setCity(addressRequestDTO.getCity());
        address.setState(addressRequestDTO.getState());
        address.setDefault(addressRequestDTO.isDefault());
        address.setPincode(addressRequestDTO.getPincode());

        Address updatedAddress = addressRepository.save(address);

        AddressResponseDTO addressResponseDTO = new AddressResponseDTO();

        addressResponseDTO.setId(updatedAddress.getId());
        addressResponseDTO.setAddressLine(updatedAddress.getAddressLine());
        addressResponseDTO.setState(updatedAddress.getState());
        addressResponseDTO.setPincode(updatedAddress.getPincode());
        addressResponseDTO.setCity(updatedAddress.getCity());
        addressResponseDTO.setDefault(updatedAddress.isDefault());
        addressResponseDTO.setType(updatedAddress.getType());
        addressResponseDTO.setUserId(updatedAddress.getUser().getId());

        return addressResponseDTO;
    }
    public void deleteAddressById(Long userId,Long addressId){
        if(!userRepository.existsById(userId)){
            throw new NotFoundException("user not found");
        }
        //not needed to store
//        addressRepository.findByIdAndUserId(addressId,userId)
//                        .orElseThrow(()->
//                                new NotFoundException("No address found corresponding to this user"));
//        addressRepository.deleteById(addressId);

        //if store then this is even better
        Address address = addressRepository.findByIdAndUserId(addressId,userId)
                      .orElseThrow(()->
                                new NotFoundException("No address found corresponding to this user"));

        addressRepository.delete(address);
    }
}
