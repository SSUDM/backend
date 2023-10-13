package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.dto.User.UserRequestDto;
import com.DM.DeveloperMatching.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/resume/{id}")
    public ResponseEntity<User> saveResume(@PathVariable Long id,
                                           @RequestBody UserRequestDto userRequestDto) {
        User savedUser = userService.saveResume(userRequestDto, id);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedUser);
    }


    @GetMapping("/resume/{id}")
    public ResponseEntity<User> getResume(@PathVariable Long id) {
        User user = userService.findUserById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    @PutMapping("/resume/{id}")
    public ResponseEntity<User> updateResume(@PathVariable Long id,
                                             @RequestBody UserRequestDto userRequestDto) {
        User updatedUser = userService.saveResume(userRequestDto, id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedUser);
    }
}
