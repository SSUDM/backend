package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.domain.Article;
import com.DM.DeveloperMatching.dto.Article.AddArticleRequest;
import com.DM.DeveloperMatching.dto.Article.UpdateArticleRequest;
import com.DM.DeveloperMatching.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class ArticleController {

    private final ArticleService articleService;

    //모집 글 생성
    @PostMapping("/articles")
    public ResponseEntity<Article> createArticle(@RequestBody AddArticleRequest articleRequest) {

        Long userId = 1L;
        Article savedArticle = articleService.save(articleRequest, userId);

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
    public ResponseEntity<Article> findArticle(@PathVariable long id) {
        Article article = articleService.findOne(id);

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

}
