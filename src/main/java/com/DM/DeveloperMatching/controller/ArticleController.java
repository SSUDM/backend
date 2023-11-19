package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.config.jwt.JwtTokenUtils;
import com.DM.DeveloperMatching.domain.Article;
import com.DM.DeveloperMatching.dto.Article.AddArticleRequest;
import com.DM.DeveloperMatching.dto.Article.ArticleResponse;
import com.DM.DeveloperMatching.dto.Article.UpdateArticleRequest;
import com.DM.DeveloperMatching.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class ArticleController {

    private final ArticleService articleService;
    private final JwtTokenUtils jwtTokenUtils;
    @Value("${jwt.secret-key}")
    private String secretKey;
//    private static String secretKey = " ";

    @GetMapping("/current-user")
    public void getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication1 = context.getAuthentication();
        System.out.println("authentication = " + authentication1);
    }

    //모집 글 생성
    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> createArticle(@RequestHeader HttpHeaders headers,
            @RequestBody AddArticleRequest articleRequest) {
//        Long userId = 1L;
        String token = headers.getFirst("Authorization");
        System.out.println("token = " + token);
        Long userId = jwtTokenUtils.extractUserId(token,secretKey);
        System.out.println("userId = " + userId);

        ArticleResponse savedArticle = articleService.save(articleRequest, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }
    //모집 글 목록 조회
    @GetMapping("/articles")
    public ResponseEntity<List<Article>> findAllArticles() {

        List<Article> articles = articleService.findAll();

        return ResponseEntity.ok()
                .body(articles);
    }

    //모집 글 단건 조회
    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        ArticleResponse article = articleService.findOne(id);

        return ResponseEntity.ok()
                .body(article);
    }

    //모집 글 수정
    @PutMapping("/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = articleService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }

    //모집 글 삭제
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        articleService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    //프로젝트 지원
    @PostMapping("/articles/{id}/apply")
    public ResponseEntity<Void> applyToProject(@PathVariable long id) {
        Long userId = 2L;
        articleService.applyToProject(userId, id);

        return new ResponseEntity(HttpStatus.OK);
    }
}
