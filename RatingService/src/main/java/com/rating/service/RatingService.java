package com.rating.service;

import com.rating.entities.Rating;

import java.util.List;

public interface RatingService {
    //create rating
    Rating saveRating(Rating rating);
    //get all rating
    List<Rating> getAllRatings();
    List<Rating> getRatingByUserId(String userId);
    List<Rating> getRatingByHotelId(String hotelId);
}
