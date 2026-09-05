package com.userservice.controllers;

import com.userservice.entities.User;
import com.userservice.services.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    // create user
    @PostMapping("/save")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = userService.createuser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    int retryInt = 1;

    // get single user
    @GetMapping("/getById/{userId}")
//    @CircuitBreaker(
//            name = "ratingHotelBreaker",
//            fallbackMethod = "ratingHotelFallback"
//    )
//    @Retry(name = "ratingHotelServiceRetry", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        logger.info("Get single user handler : UserController");
        logger.info("Retry counter: {}", retryInt++);
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);

    }

    // creating ratingHotel Fallback method for circuit breaker
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
        logger.info("Fallback is executed because service is down :", ex.getMessage());
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created dummy because some service are down.")
                .userId("12345")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);

    }


    // get all user
    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getaAllUsers());
    }

    @DeleteMapping("/deleteById/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        String message = userService.deleteUser(userId);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updateUser = userService.updateUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(updateUser);
    }

}
