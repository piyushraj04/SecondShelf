package com.secondshelf.repository;

import com.secondshelf.entity.Address;
import com.secondshelf.entity.User;
import com.secondshelf.enums.Role;
import com.secondshelf.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    boolean existsByContactNo(String contactNo);

    Optional<User> findByIdAndUserStatusAndRole(Long sellerId, UserStatus status, Role role);

}
