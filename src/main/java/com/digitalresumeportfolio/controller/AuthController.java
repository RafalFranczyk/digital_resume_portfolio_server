package com.digitalresumeportfolio.controller;

import com.digitalresumeportfolio.entity.User;
import com.digitalresumeportfolio.entity.UserProfile;
import com.digitalresumeportfolio.entity.UserRole;
import com.digitalresumeportfolio.repository.UserProfileRepository;
import com.digitalresumeportfolio.repository.UserRepository;
import com.digitalresumeportfolio.repository.UserRoleRepository;
import com.digitalresumeportfolio.request.AuthenticationRequest;
import com.digitalresumeportfolio.request.RegistrationRequest;
import com.digitalresumeportfolio.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserProfileRepository userProfileRepository;

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public ResponseEntity signIn(@RequestBody AuthenticationRequest data) {
        try {
            String usernameOrEmail = data.getUsernameOrEmail();
            boolean emailInDatabase = userRepository.findByEmail(usernameOrEmail).isPresent();
            String username;
            if (emailInDatabase) {
                User user = userRepository.findByEmail(usernameOrEmail).get();
                username = user.getUsername();
            } else {
                username = usernameOrEmail;
            }

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.getPassword()));
            User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username " + username + "not found"));
            String token = jwtTokenProvider.createToken(username, user.getRoles());

            boolean userProfileInDatabase = userProfileRepository.findByUser(user).isPresent();
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("role", user.getRoles());
            model.put("token", token);
            model.put("statusCode", "200");
            model.put("statusMessage", "Ok");
            if (userProfileInDatabase) {
                UserProfile userProfile = userProfileRepository.findByUser(user).get();
                model.put("profile", userProfile);
            } else {
                model.put("profile", "null");
            }
            return new ResponseEntity<>(model, HttpStatus.OK);
        } catch (AuthenticationException e) {
            Map<Object, Object> model = new HashMap<>();
            model.put("statusCode", "401");
            model.put("statusMessage", "Unauthorized");
            return new ResponseEntity<>(model, HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity signUp(@RequestBody RegistrationRequest data) {
        Map<Object, Object> model = new HashMap<>();
        boolean usernameInDatabase = userRepository.findByUsername(data.getUsername()).isPresent();
        boolean emailInDatabase = userRepository.findByEmail(data.getEmail()).isPresent();
        if (usernameInDatabase || emailInDatabase) {
            model.put("message", "Username or Email already exists.");
            model.put("statusCode", "409");
            model.put("statusMessage", "Conflict");
            return new ResponseEntity<>(model, HttpStatus.CONFLICT);
        } else {
            User user = new User(data.getUsername(), passwordEncoder.encode(data.getPassword()), data.getEmail());
            userRepository.save(user);
            UserRole userRole = new UserRole(user.getId(), "ROLE_USER");
            userRoleRepository.save(userRole);

            String token = jwtTokenProvider.createToken(user.getUsername(), user.getRoles());

            model.put("username", user.getUsername());
            model.put("token", token);
            model.put("role", userRole.getRoles());
            model.put("profile", "null");
            model.put("statusCode", "200");
            model.put("statusMessage", "Ok");
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AuthenticationRequest authenticationRequest) {
        Map<Object, Object> model = new HashMap<>();
        boolean userInDatabase = this.userRepository.findByUsername(userDetails.getUsername()).isPresent();
        if (userInDatabase) {
            try {
                User user = this.userRepository.findByUsername(userDetails.getUsername()).get();
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), authenticationRequest.getPassword()));
                this.userRoleRepository.deleteUserRoleByUserId(user.getId());
                this.userRepository.deleteUserById(user.getId());
                model.put("statusCode", "200");
                model.put("statusMessage", "Ok");
                return new ResponseEntity<>(model, HttpStatus.OK);
            } catch (AuthenticationException e) {
                model.put("statusCode", "400");
                model.put("statusMessage", "Bad Request");
                model.put("message", "Incorrect password.");
                return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
            }
        }

        model.put("statusCode", "400");
        model.put("statusMessage", "Bad Request");
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }
}
