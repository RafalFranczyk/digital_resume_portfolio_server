package com.digitalresumeportfolio.controller;

import com.digitalresumeportfolio.dao.User;
import com.digitalresumeportfolio.dao.UserProfile;
import com.digitalresumeportfolio.repository.UserProfileRepository;
import com.digitalresumeportfolio.repository.UserRepository;
import com.digitalresumeportfolio.request.ProfileRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/profile")
public class UserProfileController {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity getSignInProfile(@AuthenticationPrincipal UserDetails userDetails){
        Map<Object, Object> model = new HashMap<>();
        boolean userInDatabase = this.userRepository.findByUsername(userDetails.getUsername()).isPresent();
        User user;
        if (userInDatabase) {
            user = this.userRepository.findByUsername(userDetails.getUsername()).get();
            boolean userProfileInDatabase = this.userProfileRepository.findByUser(user).isPresent();
            if (userProfileInDatabase) {
                UserProfile userProfile = this.userProfileRepository.findByUser(user).get();
                model.put("profile", userProfile);
                model.put("statusCode", "200");
                model.put("statusMessage", "Ok");
                return new ResponseEntity<>(model, HttpStatus.OK);
            } else {
                model.put("profile", "null");
                model.put("statusCode", "200" );
                model.put("statusMessage", "Ok");
                return new ResponseEntity<>(model, HttpStatus.OK);
            }
        }
        model.put("statusCode", "400");
        model.put("statusMessage", "Bad Request.");
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity addProfileInformation(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ProfileRequest profileRequest) {
        Map<Object, Object> model = new HashMap<>();
        UserProfile userProfile = new UserProfile();
        userProfile.setName(profileRequest.getName());
        userProfile.setSurname(profileRequest.getSurname());
        userProfile.setBirthDate(profileRequest.getBirthDate());
        userProfile.setAddress(profileRequest.getAddress());
        userProfile.setCity(profileRequest.getCity());
        userProfile.setPhoneNumber(profileRequest.getPhoneNumber());
        userProfile.setPlaceOfBirth(profileRequest.getPlaceOfBirth());
        userProfile.setNationality(profileRequest.getNationality());
        userProfile.setPostalCode(profileRequest.getPostalCode());
        userProfile.setCountry(profileRequest.getCountry());
        boolean userInDatabase = this.userRepository.findByUsername(userDetails.getUsername()).isPresent();
        if (userInDatabase) {
            User user = this.userRepository.findByUsername(userDetails.getUsername()).get();
            boolean userProfileInDatabase = this.userProfileRepository.findByUser(user).isPresent();
            if (userProfileInDatabase) {
                model.put("statusCode", "409");
                model.put("statusMessage", "Conflict");
                model.put("message", "User profile exists.");
                return new ResponseEntity<>(model, HttpStatus.CONFLICT);
            } else {
                userProfile.setUserId(user);
                userProfileRepository.save(userProfile);
                model.put("statusCode", "200");
                model.put("statusMessage", "Ok");
                return new ResponseEntity<>(model, HttpStatus.OK);
            }
        }
        model.put("statusCode", "400");
        model.put("statusMessage", "Bad Request.");
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity updateProfileInformation(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ProfileRequest profileRequest) {
        Map<Object, Object> model = new HashMap<>();
        boolean userInDatabase = this.userRepository.findByUsername(userDetails.getUsername()).isPresent();
        if(userInDatabase) {
            User user = this.userRepository.findByUsername(userDetails.getUsername()).get();
            boolean userProfileInDatabase = this.userProfileRepository.findByUser(user).isPresent();
            if (userProfileInDatabase) {
                UserProfile userProfile = this.userProfileRepository.findByUser(user).get();
                userProfile.setBirthDate(profileRequest.getBirthDate());
                userProfile.setName(profileRequest.getName());
                userProfile.setSurname(profileRequest.getSurname());
                userProfile.setBirthDate(profileRequest.getBirthDate());
                userProfile.setPhoneNumber(profileRequest.getPhoneNumber());
                userProfile.setPlaceOfBirth(profileRequest.getPlaceOfBirth());
                userProfile.setNationality(profileRequest.getNationality());
                userProfile.setCity(profileRequest.getCity());
                userProfile.setPostalCode(profileRequest.getPostalCode());
                userProfile.setAddress(profileRequest.getAddress());
                userProfile.setCountry(profileRequest.getCountry());
                this.userProfileRepository.updateUserDetails(userProfile.getName(), userProfile.getSurname(), userProfile.getBirthDate(),
                        userProfile.getPhoneNumber(), userProfile.getPlaceOfBirth(), userProfile.getNationality(), userProfile.getCity(),
                        userProfile.getPostalCode(), userProfile.getAddress(), userProfile.getCountry(), userProfile.getUserId());
                model.put("statusCode", "200");
                model.put("statusMessage", "Ok");
                return new ResponseEntity<>(model, HttpStatus.OK);
            } else {
                model.put("statusCode", "400");
                model.put("statusMessage", "Bad Request");
                model.put("message", "User profile don't exists.");
                return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
            }
        }
        model.put("statusCode", "400");
        model.put("statusMessage", "Bad Request.");
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }
}
