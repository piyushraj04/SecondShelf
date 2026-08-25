package com.secondshelf.repository;

import com.secondshelf.entity.BookListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookListingRepository extends JpaRepository<BookListing,Long> {
}
