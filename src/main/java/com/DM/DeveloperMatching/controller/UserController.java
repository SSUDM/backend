package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.dto.User.UserRequestDto;
import com.DM.DeveloperMatching.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/resume")
    public ResponseEntity<User> saveResume(@RequestBody UserRequestDto userRequestDto) {
        Long userId = 1L;
        User savedUser = userService.saveResume(userRequestDto, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedUser);
    }
}
