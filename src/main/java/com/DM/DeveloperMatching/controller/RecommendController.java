package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.domain.Article;
import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.dto.Recommend.RecommendProjectDto;
import com.DM.DeveloperMatching.dto.Recommend.RecommendRequest;
import com.DM.DeveloperMatching.dto.Recommend.RecommendUserDto;
import com.DM.DeveloperMatching.service.RecommendService;
import com.DM.DeveloperMatching.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class RecommendController {
    private final RecommendService recommendService;
    private final UserService userService;

    @GetMapping(value = "/rec-project")
    public ResponseEntity<List<RecommendProjectDto>> recommendProject(@RequestBody(required = false) RecommendRequest request) {
        Long userId = 1L;
        if(request == null) {
            List<Article> articles = recommendService.recommendProjectByCS(userId);
            List<RecommendProjectDto> recommendDtos = articles.stream()
                    .map(RecommendProjectDto::toDto)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(recommendDtos);
        }
        else {
            List<Article> articles = recommendService.recommendProjectByCS(userId, request.getRecPart(),
                    request.getRecTech(),
                    request.getRecLevel());

            List<RecommendProjectDto> recommendDtos = articles.stream()
                    .map(RecommendProjectDto::toDto)
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.OK)
                    .body(recommendDtos);
        }
    }

    @GetMapping(value = "/rec-teammate")
    public ResponseEntity<List<RecommendUserDto>> recommendUser() {
        Long userId = 1L;
        List<User> users = recommendService.recommendUserByCS(userId);
        List<RecommendUserDto> recommendDtos = users.stream()
                .map(RecommendUserDto::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .body(recommendDtos);
    }
}
