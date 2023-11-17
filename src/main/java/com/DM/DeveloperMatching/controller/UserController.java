package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.dto.User.UserRequestDto;
import com.DM.DeveloperMatching.dto.User.UserResponseDto;
import com.DM.DeveloperMatching.service.RecommendService;
import com.DM.DeveloperMatching.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/resume")
    public ResponseEntity<UserResponseDto> saveResume(@RequestBody UserRequestDto userRequestDto) {
        Long userId = 1L;
        User savedUser = userService.saveResume(userRequestDto, userId);

        UserResponseDto userResponseDto = new UserResponseDto(savedUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userResponseDto);
    }


    @GetMapping("/resume/{id}")
    public ResponseEntity<UserResponseDto> getResume(@PathVariable Long id) {
        User user = userService.findUserById(id);

        UserResponseDto userResponseDto = new UserResponseDto(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userResponseDto);
    }

    @PutMapping("/resume/{id}")
    public ResponseEntity<UserResponseDto> updateResume(@PathVariable Long id,
                                             @RequestBody UserRequestDto userRequestDto) {
        User updatedUser = userService.saveResume(userRequestDto, id);

        UserResponseDto userResponseDto = new UserResponseDto(updatedUser);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userResponseDto);
    }
}
