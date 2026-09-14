package com.secondshelf.repository;

import com.secondshelf.entity.BookListing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookListingRepository extends JpaRepository<BookListing,Long> {

    Optional<List<BookListing>> findBySellerId(Long sellerid);

    @Query("select b from BookListing b where b.id =:listingId And b.seller.id =: sellerId")
    Optional<BookListing> findByIdAndSellerId(Long listingId, Long sellerId);

}
