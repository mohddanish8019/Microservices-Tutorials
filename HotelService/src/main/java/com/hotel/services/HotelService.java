package com.hotel.services;

import com.hotel.enties.Hotel;

import java.util.List;

public interface HotelService {

    //create hotel
    Hotel createHotel(Hotel hotel);

    //get allhotels
    List<Hotel> getAllHotels();

    //single hotel fetch
    Hotel findById(String id);

    String deleteById(String id);

}