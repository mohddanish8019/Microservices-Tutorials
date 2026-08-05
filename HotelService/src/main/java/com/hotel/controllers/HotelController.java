package com.hotel.controllers;

import com.hotel.enties.Hotel;
import com.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;


    @PostMapping("/save")
    public ResponseEntity<Hotel> saveHotel(@RequestBody Hotel hotel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.createHotel(hotel));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Hotel>> getHotels() {
        List<Hotel> hotelList = hotelService.getAllHotels();
        return new ResponseEntity<>(hotelList, HttpStatus.OK);
    }

    @GetMapping("/getById/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String hotelId) {
        Hotel hotel = hotelService.findById(hotelId);
        return new ResponseEntity<>(hotel, HttpStatus.OK);
    }

    @DeleteMapping("/deleteById/{hotelId}")
    public ResponseEntity<String> deleteHotelById(@PathVariable String hotelId) {
        hotelService.deleteById(hotelId);
        return new ResponseEntity<>("Hotel has been deleted", HttpStatus.OK);
    }
}
