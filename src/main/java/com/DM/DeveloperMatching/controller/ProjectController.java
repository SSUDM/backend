package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.config.jwt.JwtTokenUtils;
import com.DM.DeveloperMatching.dto.Project.ProjectResponse;
import com.DM.DeveloperMatching.dto.Project.TeamMate;
import com.DM.DeveloperMatching.dto.User.UserRequestDto;;
import com.DM.DeveloperMatching.service.ProjectService;
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
public class ProjectController {

    private final ProjectService projectService;
    private final JwtTokenUtils jwtTokenUtils;
    @Value("${jwt.secret-key}")
    private String secretKey;

    //프로젝트 지원
    @PostMapping("/project/{pId}/apply")
    public ResponseEntity<Void> applyToProject(@RequestHeader HttpHeaders headers, @PathVariable long pId) {
        String token = headers.getFirst("Authorization");
        Long uId = jwtTokenUtils.extractUserId(token,secretKey);
        projectService.applyToProject(uId, pId);

        return new ResponseEntity(HttpStatus.OK);
    }

    //프로젝트 종료
    @PostMapping("/project/{pId}/terminate")
    public ResponseEntity<Void> terminateProject(@PathVariable long pId) {
        projectService.terminateProject(pId);

        return new ResponseEntity(HttpStatus.OK);
    }

    //프로젝트 팀원 조회
    @GetMapping("/project/{pId}/get-teammates")
    public ResponseEntity<List<TeamMate>> getTeamMates(@PathVariable long pId) {
        List<TeamMate> teamMates = projectService.getTeamMates(pId);

        return ResponseEntity.ok()
                .body(teamMates);
    }

    //프로젝트 지원한 인원 조회
    @GetMapping("/project/{pId}/get-apply-request")
    public ResponseEntity<List<UserRequestDto>> getAppliedUsers(@PathVariable long pId) {
        List<UserRequestDto> appliedUsers = projectService.getAppliedUsers(pId);

        return ResponseEntity.ok()
                .body(appliedUsers);
    }

    //인기 프로젝트 조회
    @GetMapping("/project/get-pop-projects")
    public ResponseEntity<List<ProjectResponse>> getPopularProjects() {
        List<ProjectResponse> popularProjects = projectService.getPopularProjects();

        return ResponseEntity.ok()
                .body(popularProjects);
    }

    //유저가 한 프로젝트 전체 조회
    @GetMapping("/project/get-my-projects")
    public ResponseEntity<List<ProjectResponse>> getMyProjects(@RequestHeader HttpHeaders headers) {
        String token = headers.getFirst("Authorization");
        Long uId = jwtTokenUtils.extractUserId(token,secretKey);
        List<ProjectResponse> projects = projectService.getAllUserProjects(uId);

        return ResponseEntity.ok()
                .body(projects);
    }

    //프로젝트 참가 요청 수락
    @PostMapping("/project/accept-join-project/{pId}")
    public ResponseEntity<String> acceptJoinProject(@RequestHeader HttpHeaders headers, @PathVariable long pId) {
        String token = headers.getFirst("Authorization");
        Long uId = jwtTokenUtils.extractUserId(token,secretKey);
        String result = projectService.acceptJoinProject(uId, pId);

        return ResponseEntity.ok()
                .body(result);
    }


    // 프로젝트 참가 요청 거절
    @PostMapping("/project/reject-join-project/{pId}")
    public ResponseEntity<String> rejectJoinProject(@RequestHeader HttpHeaders headers, @PathVariable long pId) {
        String token = headers.getFirst("Authorization");
        Long uId = jwtTokenUtils.extractUserId(token,secretKey);
        String result = projectService.rejectJoinProject(uId, pId);

        return ResponseEntity.ok()
                .body(result);
    }

    //협업 요청 수락


    //협업 요청 거절


}