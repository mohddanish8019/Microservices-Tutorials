package com.rating.controllers;

import com.rating.entities.Rating;
import com.rating.repositories.RatingRepository;
import com.rating.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping("/save")
    public ResponseEntity<Rating> saveRating(@RequestBody Rating rating)
    {
        return  new ResponseEntity<>(ratingService.saveRating(rating), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Rating>>  getAllRatings()
    {
        return new ResponseEntity<>(ratingService.getAllRatings(), HttpStatus.OK);
    }

    @GetMapping("/getByHotelId/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable String hotelId){
        return new ResponseEntity<>(ratingService.getRatingByHotelId(hotelId), HttpStatus.OK);
    }
    @GetMapping("/getByUserId/{userId}")
    public  ResponseEntity<List<Rating>> getAllRatingsByUserId(@PathVariable String userId){
        return new ResponseEntity<>(ratingService.getRatingByUserId(userId), HttpStatus.OK);
    }

}


