package com.secondshelf.repository;

import com.secondshelf.entity.BookListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookListingRepository extends JpaRepository<BookListing,Long> {

    Optional<List<BookListing>> findBySellerId(Long sellerid);
    @Query("""
       SELECT b
       FROM BookListing b
       WHERE b.id = :listingId
       AND b.seller.id = :sellerId
       """)
    Optional<BookListing> findByIdAndSellerId(
            @Param("listingId") Long listingId,
            @Param("sellerId") Long sellerId);

}
