package com.rating.repositories;

import com.rating.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, String>
{
    List<Rating> findByUserId(String userId);
    List<Rating> findByHotelId(String hotelId);

}
