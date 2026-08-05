package com.userservice.services.impl;

import com.userservice.entities.Hotel;
import com.userservice.entities.Rating;
import com.userservice.entities.User;
import com.userservice.exception.ResourceNotFoundException;
import com.userservice.externalservices.HotelService;
import com.userservice.repositories.UserRepository;
import com.userservice.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private HotelService hotelService;

    @Override
    public User createuser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getaAllUsers() {

        // implement rating service call using reasttemplate.
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String userId) {
        //get user from db with the help of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with given id is not found on sever!! " + userId));
//        fetching rating of the above user from rating service
//        http://localhost:8082/ratings/getByUserId/67cb2c18-af3a-4f3d-a74d-2b9189ab0a9e
        Rating[] ratingOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/getByUserId/" + user.getUserId(), Rating[].class);
        logger.info("{}", ratingOfUser);

        List<Rating> ratings = Arrays.stream(ratingOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to hotel service to get the hotel
//          http://localhost:8081/hotels/getById/1e7af51c-bfbe-42f4-b37b-5fc71fe1bfbc
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/getById/" + rating.getHotelId(), Hotel.class);
            Hotel hotel = hotelService.findHotelById(rating.getHotelId()); //forEntity.getBody();;
//            logger.info("hotel status code : {}", forEntity.getStatusCode());
            rating.setHotel(hotel);
            //set hotel to rating;
            return rating;
        }).collect(Collectors.toList());


        user.setRatings(ratingList);


        return user;


    }

    @Override
    public String deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with given id is not found on sever!! " + userId));
        userRepository.delete(user);
        return "User deleted successfully";
    }

    @Override
    public User updateUser(User user) {
        User updateUser = userRepository.findById(user.getUserId()).orElseThrow(() -> new ResourceNotFoundException("user with given id is not found on sever!! " + user.getUserId()));
        updateUser.setName(user.getName());
        updateUser.setAbout(user.getAbout());
        userRepository.save(updateUser);
        return updateUser;
    }

}
