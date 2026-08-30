package com.secondshelf.repository;

import com.secondshelf.entity.Address;
import com.secondshelf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    boolean existsByContactNo(String contactNo);
}
