package com.digitalresumeportfolio.controller;

import com.digitalresumeportfolio.entity.Resume;
import com.digitalresumeportfolio.entity.User;
import com.digitalresumeportfolio.repository.ResumeRepository;
import com.digitalresumeportfolio.repository.UserRepository;
import com.digitalresumeportfolio.request.ResumeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ResumeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResumeRepository resumeRepository;

    @RequestMapping(value = "/resume", method = RequestMethod.POST)
    public ResponseEntity addResume(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ResumeRequest resumeRequest) {
        Map<Object, Object> model = new HashMap<>();
        boolean userInDatabase = userRepository.findByUsername(userDetails.getUsername()).isPresent();
        if (userInDatabase) {
            User user = userRepository.findByUsername(userDetails.getUsername()).get();
            Resume resume = new Resume(user, resumeRequest.getName(), resumeRequest.getDescription());
            resumeRepository.save(resume);
            model.put("statusCode", "200");
            model.put("statusMessage", "Ok");
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        model.put("statusCode", "400");
        model.put("statusMessage", "Bad Request.");
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/resumes", method = RequestMethod.GET)
    public ResponseEntity getUserResumes(@AuthenticationPrincipal UserDetails userDetails) {
        Map<Object, Object> model = new HashMap<>();
        boolean userInDatabase = userRepository.findByUsername(userDetails.getUsername()).isPresent();
        if (userInDatabase) {
            User user = userRepository.findByUsername(userDetails.getUsername()).get();
            boolean resumesInDatabase = resumeRepository.findByUser(user).isPresent();
            if (resumesInDatabase) {
                List<Resume> resumes = resumeRepository.findByUser(user).get();
                model.put("resumes", resumes);
                model.put("statusCode", "200");
                model.put("statusMessage", "Ok");
                return new ResponseEntity<>(model, HttpStatus.OK);
            }
        }
        model.put("statusCode", "400");
        model.put("statusMessage", "Bad Request.");
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/resume", method = RequestMethod.GET)
    public ResponseEntity getResumeById(@RequestParam  Long id) {
        Map<Object, Object> model = new HashMap<>();

        boolean resumeInDatabase = resumeRepository.findById(id).isPresent();
        if (resumeInDatabase) {
            Resume resume = resumeRepository.findById(id).get();
            model.put("resume", resume);
            model.put("statusCode", "200");
            model.put("statusMessage", "Ok");
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        model.put("statusCode", "400");
        model.put("statusMessage", "Bad Request.");
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }
}
