package com.DM.DeveloperMatching.service;

import com.DM.DeveloperMatching.domain.Article;
import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.dto.Article.AddArticleRequest;
import com.DM.DeveloperMatching.repository.ArticleRepository;
import com.DM.DeveloperMatching.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    //모집 글 생성
    public Article save(AddArticleRequest articleRequest, Long uId) {
        User user = userRepository.findById(uId).orElseThrow(() -> new IllegalArgumentException("not found user"));

        return articleRepository.save(articleRequest.toEntity(user));
    }
}
