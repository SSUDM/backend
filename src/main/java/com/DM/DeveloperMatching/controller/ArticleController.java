package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.domain.Article;
import com.DM.DeveloperMatching.dto.Article.AddArticleRequest;
import com.DM.DeveloperMatching.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
