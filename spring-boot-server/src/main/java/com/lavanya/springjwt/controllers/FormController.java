package com.lavanya.springjwt.controllers;

import com.lavanya.springjwt.models.FormDocument;
import com.lavanya.springjwt.models.User;
import com.lavanya.springjwt.security.services.UserDetailsImpl;
import com.lavanya.springjwt.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import static com.lavanya.springjwt.models.ERole.ROLE_ADMIN;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/submitForm")
public class FormController {

    @Autowired
    private FormService formService;

    @PostMapping("/submit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<FormDocument> submitFeedback(@RequestBody FormDocument form) {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            form.setCommentDate(now);
            FormDocument userForm = formService.save(form);
            return new ResponseEntity<>(form, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/showFeedback")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> showFeedback(@RequestParam Integer id) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl currentUsername = (UserDetailsImpl) authentication.getPrincipal();
            if(currentUsername.getId()!=(long)id){
                return new ResponseEntity<>("You do not have permission to view this feedback", HttpStatus.FORBIDDEN);
            }
            List<FormDocument> feedbackForm;
            if(currentUsername.getAuthorities().equals(ROLE_ADMIN)){
                feedbackForm = formService.findAll();
            }
            else{
                feedbackForm = formService.findAllById(id);
            }

            if(feedbackForm.isEmpty()){
                return new ResponseEntity<>("No feedback found for the user", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(feedbackForm, HttpStatus.OK);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/showAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> showAll() {
        try {
            List<FormDocument> feedbacks = formService.findAll();
            return new ResponseEntity<>(feedbacks, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
