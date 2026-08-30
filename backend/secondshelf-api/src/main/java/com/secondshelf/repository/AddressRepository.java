package com.secondshelf.repository;

import com.secondshelf.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findByUserId(Long userId);
    Optional<Address> findByIdAndUserId(Long addressId, Long userId);
    Optional<Address> findByuserIdAndIsDefaultTrue(Long userId);

}
