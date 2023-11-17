package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.domain.Article;
import com.DM.DeveloperMatching.dto.Article.AddArticleRequest;
import com.DM.DeveloperMatching.dto.Article.ArticleResponse;
import com.DM.DeveloperMatching.dto.Article.UpdateArticleRequest;
import com.DM.DeveloperMatching.service.ArticleService;
import lombok.RequiredArgsConstructor;
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

    //모집 글 생성
    @PostMapping("/articles")
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody AddArticleRequest articleRequest) {

        Long userId = 1L;
        Article savedArticle = articleService.save(articleRequest, userId);
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
    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = articleService.findOne(id);
        ArticleResponse articleResponse = new ArticleResponse(article);

        return ResponseEntity.ok()
                .body(articleResponse);
    }

    //모집 글 수정
    @PutMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = articleService.update(id, request);
        ArticleResponse updatedArticleResponse = new ArticleResponse(updatedArticle);

        return ResponseEntity.ok()
                .body(updatedArticleResponse);
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
