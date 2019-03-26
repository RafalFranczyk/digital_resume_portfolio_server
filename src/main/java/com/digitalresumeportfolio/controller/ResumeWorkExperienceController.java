package com.digitalresumeportfolio.controller;

import com.digitalresumeportfolio.dao.Resume;
import com.digitalresumeportfolio.dao.ResumeWorkExperience;
import com.digitalresumeportfolio.dao.User;
import com.digitalresumeportfolio.repository.ResumeRepository;
import com.digitalresumeportfolio.repository.ResumeWorkExperienceRepository;
import com.digitalresumeportfolio.repository.UserRepository;
import com.digitalresumeportfolio.request.ResumeWorkExperienceRequest;
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
public class ResumeWorkExperienceController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResumeRepository resumeRepository;

    @Autowired
    ResumeWorkExperienceRepository resumeWorkExperienceRepository;

    @RequestMapping(value = "/resumeworkexperience", method = RequestMethod.POST)
    public ResponseEntity addResumeWorkExperience(@AuthenticationPrincipal UserDetails userDetails,
                                                  @RequestBody ResumeWorkExperienceRequest resumeWorkExperienceRequest) {

        Map<Object, Object> model = new HashMap<>();
        boolean userInDatabase = userRepository.findByUsername(userDetails.getUsername()).isPresent();
        if (userInDatabase) {
            User user = userRepository.findByUsername(userDetails.getUsername()).get();
            boolean resumeInDatabase = resumeRepository.findById(resumeWorkExperienceRequest.getResumeId()).isPresent();
            if (resumeInDatabase) {
                Resume resume = resumeRepository.findById(resumeWorkExperienceRequest.getResumeId()).get();
                if (resume.getUser().getId().equals(user.getId())) {
                    ResumeWorkExperience resumeWorkExperience = new ResumeWorkExperience(
                            resumeWorkExperienceRequest.getStartDate(), resumeWorkExperienceRequest.getEndDate(),
                            resumeWorkExperienceRequest.getCompanyName(), resumeWorkExperienceRequest.getWorkTitle(),
                            resumeWorkExperienceRequest.getWorkDescription(), resume);
                    resumeWorkExperienceRepository.save(resumeWorkExperience);
                    model.put("resumeWorkExperience", resumeWorkExperience);
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
            model.put("message", "Resume don't exist.");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        model.put("statusCode", "400");
        model.put("statusMessage", "Bad Request.");
        model.put("message", "User don't exist.");
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/resumeworkexperience", method = RequestMethod.GET)
    public ResponseEntity getResumeWorkExperienceById(@RequestParam Long id) {
        Map<Object, Object> model = new HashMap<>();

        boolean resumeWorkExperienceInDatabase = resumeWorkExperienceRepository.findById(id).isPresent();
        if (resumeWorkExperienceInDatabase) {
            ResumeWorkExperience resumeWorkExperience = resumeWorkExperienceRepository.findById(id).get();
            model.put("resumeWorkExperience", resumeWorkExperience);
            model.put("statusCode", "200");
            model.put("statusMessage", "Ok");
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        model.put("statusCode", "400");
        model.put("statusMessage", "Bad Request.");
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/resumeworkexperiences", method = RequestMethod.GET)
    public ResponseEntity getResumeWorkExperienceByResume(@RequestParam Long id) {
        Map<Object, Object> model = new HashMap<>();
        boolean resumesInDatabase = resumeRepository.findById(id).isPresent();
        if (resumesInDatabase) {
            Resume resume = resumeRepository.findById(id).get();
            boolean resumeWorkExperience = resumeWorkExperienceRepository.findByResume(resume).isPresent();
            if (resumeWorkExperience) {
                List<ResumeWorkExperience> resumeWorkExperiences = resumeWorkExperienceRepository.findByResume(resume).get();
                model.put("resumeWorkExperiences", resumeWorkExperiences);
                model.put("statusCode", "200");
                model.put("statusMessage", "Ok");
                return new ResponseEntity<>(model, HttpStatus.OK);
            }
            model.put("statusCode", "400");
            model.put("statusMessage", "Bad Request.");
            model.put("message", "lol");
            return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
        }
        model.put("statusCode", "400");
        model.put("statusMessage", "Bad Request.");
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "resumeworkexperience/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateResumeWorkExperienceById(@PathVariable Long id, @RequestBody ResumeWorkExperienceRequest resumeWorkExperienceRequest) {
        Map<Object, Object> model = new HashMap<>();
        boolean resumeWorkExperienceInDatabase = resumeWorkExperienceRepository.findById(id).isPresent();
        if (resumeWorkExperienceInDatabase) {
            resumeWorkExperienceRepository.updateResumeWorkExperienceById(id, resumeWorkExperienceRequest.getStartDate(),
                    resumeWorkExperienceRequest.getEndDate(), resumeWorkExperienceRequest.getCompanyName(),
                    resumeWorkExperienceRequest.getWorkTitle(), resumeWorkExperienceRequest.getWorkDescription());
            model.put("statusCode", "200");
            model.put("statusMessage", "Ok");
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        model.put("statusCode", "400");
        model.put("statusMessage", "Bad Request.");
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "resumeworkexperience/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteResumeWorkExperienceById(@PathVariable Long id) {
        Map<Object, Object> model = new HashMap<>();
        boolean resumeWorkExperienceInDatabase = resumeWorkExperienceRepository.findById(id).isPresent();
        if (resumeWorkExperienceInDatabase) {
            resumeWorkExperienceRepository.deleteResumeWorkExperienceById(id);
            model.put("statusCode", "200");
            model.put("statusMessage", "Ok");
            return new ResponseEntity<>(model, HttpStatus.OK);
        }
        model.put("statusCode", "400");
        model.put("statusMessage", "Bad Request.");
        return new ResponseEntity<>(model, HttpStatus.BAD_REQUEST);
    }

}
