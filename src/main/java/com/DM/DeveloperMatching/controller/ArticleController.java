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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class ArticleController {

    private final ArticleService articleService;
    private final JwtTokenUtils jwtTokenUtils;
    @Value("${jwt.secret-key}")
    private String secretKey;

    //모집 글 생성
    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> createArticle(@RequestHeader HttpHeaders headers,
                                                         @RequestBody AddArticleRequest articleRequest) {
        String token = headers.getFirst("Authorization");
        Long uId = jwtTokenUtils.extractUserId(token,secretKey);

        Article savedArticle = articleService.save(articleRequest, uId);
        ArticleResponse articleResponse = new ArticleResponse(savedArticle);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(articleResponse);
    }

    //모집 글 목록 조회
    @GetMapping("/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {

        List<Article> articles = articleService.findAll();
        List<ArticleResponse> articleResponses = articles.stream()
                .map(article -> new ArticleResponse(article))
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(articleResponses);
    }

    //모집 글 단건 조회
    @GetMapping("/articles/{aId}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long aId) {
        Article article = articleService.findOne(aId);
        ArticleResponse articleResponse = new ArticleResponse(article);

        return ResponseEntity.ok()
                .body(articleResponse);
    }

    //모집 글 수정
    @PutMapping("/articles/{aId}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable long aId,
                                                         @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = articleService.update(aId, request);
        ArticleResponse updatedArticleResponse = new ArticleResponse(updatedArticle);

        return ResponseEntity.ok()
                .body(updatedArticleResponse);
    }

    //모집 글 삭제
    @DeleteMapping("/articles/{aId}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long aId) {
        articleService.delete(aId);

        return ResponseEntity.ok()
                .build();
    }

}
