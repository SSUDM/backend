package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.dto.Project.TeamMate;
import com.DM.DeveloperMatching.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class ProjectController {

    private final ProjectService projectService;

    //프로젝트 팀원 조회
    @GetMapping("/project/get-teammates")
    public ResponseEntity<List<TeamMate>> getTeamMates(Long pId) {
        List<TeamMate> teamMates = projectService.getTeamMates(pId);

        return ResponseEntity.ok()
                .body(teamMates);
    }

    //인기 프로젝트 조회


}