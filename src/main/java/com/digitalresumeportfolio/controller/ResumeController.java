package com.digitalresumeportfolio.controller;

import com.digitalresumeportfolio.dao.Resume;
import com.digitalresumeportfolio.dao.User;
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
    public ResponseEntity getResumeById(@RequestParam Long id) {
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

    @RequestMapping(value = "/resume/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateResumeById(@PathVariable Long id, @RequestBody ResumeRequest resumeRequest, @AuthenticationPrincipal UserDetails userDetails) {
        Map<Object, Object> model = new HashMap<>();
        boolean resumeInDatabase = resumeRepository.findById(id).isPresent();
        boolean userInDatabase = userRepository.findByUsername(userDetails.getUsername()).isPresent();
        if (resumeInDatabase && userInDatabase) {
            Resume resume = resumeRepository.findById(id).get();
            User user = userRepository.findByUsername(userDetails.getUsername()).get();
            if (resume.getUser().getId().equals(user.getId())) {
                resume.setName(resumeRequest.getName());
                resume.setDescription(resumeRequest.getDescription());
                resumeRepository.updateResumeById(id, resume.getName(), resume.getDescription());
                model.put("resume", resume);
                model.put("statusCode", "200");
                model.put("statusMessage", "Ok");
                return new ResponseEntity<>(model, HttpStatus.OK);
            }
            model.put("statusCode", "401");
            model.put("statusMessage", "Unauthorized");
            return new ResponseEntity<>(model, HttpStatus.UNAUTHORIZED);
        }
        model.put("statusCode", "400");
        model.put("statusMessage", "Bad Request.");
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/resume/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteResumeById(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Map<Object, Object> model = new HashMap<>();
        boolean resumeInDatabase = resumeRepository.findById(id).isPresent();
        boolean userInDatabase = userRepository.findByUsername(userDetails.getUsername()).isPresent();
        if (resumeInDatabase && userInDatabase) {
            Resume resume = resumeRepository.findById(id).get();
            User user = userRepository.findByUsername(userDetails.getUsername()).get();
            if (resume.getUser().getId().equals(user.getId())) {
                resumeRepository.deleteResumeById(resume.getId());
                model.put("statusCode", "200");
                model.put("statusMessage", "Ok");
                return new ResponseEntity<>(model, HttpStatus.OK);
            }
            model.put("statusCode", "401");
            model.put("statusMessage", "Unauthorized");
            return new ResponseEntity<>(model, HttpStatus.UNAUTHORIZED);
        }
        model.put("statusCode", "400");
        model.put("statusMessage", "Bad Request.");
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }


}
