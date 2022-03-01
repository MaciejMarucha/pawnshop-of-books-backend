package com.back.controller;

import com.back.model.Book;
import com.back.model.User;
import com.back.repository.BookRepository;
import com.back.repository.UserRepository;
import com.back.exception.ResourceNotFoundException;
import com.back.payload.UserIdentityAvailability;
import com.back.payload.UserProfile;
import com.back.payload.UserSummary;
import com.back.security.CurrentUser;
import com.back.security.UserPrincipal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;
    private BookRepository bookRepository;

    public UserController(UserRepository userRepository, BookRepository bookRepository){
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername());

        return userSummary;
    }

    @GetMapping("/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }


    @GetMapping("/profile/{username}")
    public UserProfile getUserProfile(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        long addedBooksCount = bookRepository.findByWhoAddedIt(user).size();
        long boughtBooksCount = bookRepository.findByWhoBoughtIt(user).size();

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), addedBooksCount, boughtBooksCount);

        return userProfile;
    }

    @GetMapping("/profile/{username}/added")
    public List<Book> getBooksAddedBy(@PathVariable String username,
                                      @CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User","username",username));
        return bookRepository.findByWhoAddedIt(user);
    }


    @GetMapping("/profile/{username}/bought")
    public List<Book> getBooksBoughtBy(@PathVariable String username,
                                      @CurrentUser UserPrincipal currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User","username",username));
        return bookRepository.findByWhoBoughtIt(user);
    }

}
