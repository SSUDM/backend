package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.dto.User.UserInfoResponse;
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

    //내 정보 보기
    @GetMapping("/user/info")
    public ResponseEntity<UserInfoResponse> getUserInfo() {
        Long userId = 1L;
        UserInfoResponse userInfo = userService.getUserInfo(userId);

        return ResponseEntity.ok()
                .body(userInfo);
    }

    //이력서 저장
    @PostMapping("/resume")
    public ResponseEntity<User> saveResume(@RequestBody UserRequestDto userRequestDto) {
        Long userId = 1L;
        User savedUser = userService.saveResume(userRequestDto, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedUser);
    }

    //이력서 조회
    @GetMapping("/resume/{id}")
    public ResponseEntity<User> getResume(@PathVariable Long id) {
        User user = userService.findUserById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    //이력서 수정
    @PutMapping("/resume/{id}")
    public ResponseEntity<User> updateResume(@PathVariable Long id,
                                             @RequestBody UserRequestDto userRequestDto) {
        User updatedUser = userService.saveResume(userRequestDto, id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedUser);
    }

    //내 프로젝트 목록

}