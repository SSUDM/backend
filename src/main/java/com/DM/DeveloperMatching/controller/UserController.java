package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.config.jwt.JwtTokenUtils;
import com.DM.DeveloperMatching.domain.Project;
import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.dto.Project.ProjectResponse;
import com.DM.DeveloperMatching.dto.User.UserInfoResponse;
import com.DM.DeveloperMatching.dto.User.UserRequestDto;
import com.DM.DeveloperMatching.service.ProjectService;
import com.DM.DeveloperMatching.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class UserController {

    private final UserService userService;
    private final ProjectService projectService;
    private final JwtTokenUtils jwtTokenUtils;

    @Value("${jwt.secret-key}")
    private String secretKey;


    //내 정보 보기
    @GetMapping("/user/info")
    public ResponseEntity<UserInfoResponse> getUserInfo(@RequestHeader HttpHeaders headers) {
        String token = headers.getFirst("Authorization");
        Long uId = jwtTokenUtils.extractUserId(token,secretKey);
        UserInfoResponse userInfo = userService.getUserInfo(uId);

        return ResponseEntity.ok()
                .body(userInfo);
    }

    //내 프로젝트 목록 조회
    @GetMapping("/user/project")
    public ResponseEntity<List<ProjectResponse>> getMyProjects(@RequestHeader HttpHeaders headers) {
        String token = headers.getFirst("Authorization");
        Long uId = jwtTokenUtils.extractUserId(token,secretKey);
        List<ProjectResponse> projects = projectService.getAllUserProjects(uId);

        return ResponseEntity.ok()
                .body(projects);
    }

    //이력서 저장
    @PostMapping("/resume")
    public ResponseEntity<User> saveResume(@RequestHeader HttpHeaders headers, @RequestBody UserRequestDto userRequestDto) {
        String token = headers.getFirst("Authorization");
        Long uId = jwtTokenUtils.extractUserId(token,secretKey);
        User savedUser = userService.saveResume(userRequestDto, uId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedUser);
    }

    //이력서 조회
    @GetMapping("/resume")
    public ResponseEntity<User> getResume(@RequestHeader HttpHeaders headers) {
        String token = headers.getFirst("Authorization");
        Long uId = jwtTokenUtils.extractUserId(token,secretKey);
        User user = userService.findUserById(uId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    //이력서 수정
    @PutMapping("/resume")
    public ResponseEntity<User> updateResume(@RequestHeader HttpHeaders headers, @RequestBody UserRequestDto userRequestDto) {
        String token = headers.getFirst("Authorization");
        Long uId = jwtTokenUtils.extractUserId(token,secretKey);
        User updatedUser = userService.saveResume(userRequestDto, uId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(updatedUser);
    }


}