package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.dto.User.UserRequestDto;
import com.DM.DeveloperMatching.dto.User.UserResponseDto;
import com.DM.DeveloperMatching.service.RecommendService;
import com.DM.DeveloperMatching.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;

    @Value("${jwt.secret-key}")
    private String secretKey;

//
//    //내 정보 보기
//    @GetMapping("/user/info")
//    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestHeader HttpHeaders headers) {
//        String token = headers.getFirst("Authorization");
//        Long uId = jwtTokenUtils.extractUserId(token,secretKey);
//        UserInfoResponse userInfo = userService.getUserInfo(uId);
//
//        return ResponseEntity.ok()
//                .body(userInfo);
//    }
//
//    //내 프로젝트 목록 조회
//    @GetMapping("/user/project")
//    public ResponseEntity<List<ProjectResponse>> getMyProjects(@RequestHeader HttpHeaders headers) {
//        String token = headers.getFirst("Authorization");
//        Long uId = jwtTokenUtils.extractUserId(token,secretKey);
//        List<ProjectResponse> projects = projectService.getAllUserProjects(uId);
//
//        return ResponseEntity.ok()
//                .body(projects);
//    }

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