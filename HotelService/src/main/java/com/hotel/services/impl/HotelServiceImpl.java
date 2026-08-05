package com.hotel.services.impl;

import com.hotel.enties.Hotel;
import com.hotel.exceptions.ResourceNotFoundException;
import com.hotel.repositories.HotelRepository;
import com.hotel.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel createHotel(Hotel hotel) {
        String hotelId=UUID.randomUUID().toString();
        hotel.setHotelId(hotelId);
        return hotelRepository.save(hotel)  ;
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel findById(String id) {
        return hotelRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Hotel with given Id not found on server !! "+id));
    }

    @Override
    public String deleteById(String id) {
        Hotel hotel=hotelRepository.findById(id).orElseThrow(() ->  new ResourceNotFoundException("Hotel with given Id not found on server !! "+id));
        hotelRepository.delete(hotel);
        return "Hotel has been deleted";
    }
}
