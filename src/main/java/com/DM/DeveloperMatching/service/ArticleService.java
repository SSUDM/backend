package com.DM.DeveloperMatching.service;

import com.DM.DeveloperMatching.domain.Article;
import com.DM.DeveloperMatching.domain.Member;
import com.DM.DeveloperMatching.domain.MemberStatus;
import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.dto.Article.AddArticleRequest;
import com.DM.DeveloperMatching.dto.Article.ArticleResponse;
import com.DM.DeveloperMatching.dto.Article.UpdateArticleRequest;
import com.DM.DeveloperMatching.repository.ArticleRepository;
import com.DM.DeveloperMatching.repository.MemberRepository;
import com.DM.DeveloperMatching.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    //모집 글 생성
    public Article save(AddArticleRequest articleRequest, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found user"));

        Article savedArticle = articleRepository.save(articleRequest.toEntity(user));
        Member member = Member.builder()
                .memberStatus(MemberStatus.ACCEPTED)
                .user(user)
                .project(savedArticle.getProject())
                .build();
        memberRepository.save(member);

        return savedArticle;
    }

    //모집 글 목록 조회
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    //모집 글 단건 조회
    public ArticleResponse findOne(Long userId) {
        Article article = articleRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("not found article"));
        ArticleResponse articleResponse = new ArticleResponse(article);

        return articleResponse;
    }

    //모집 글 수정
    public Article update(Long articleId, UpdateArticleRequest updateArticleRequest) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("not found article"));

        article.update(updateArticleRequest.getTitle(), updateArticleRequest.getMaximumMember(),
                updateArticleRequest.getRecPart(), updateArticleRequest.getRecTech(),
                updateArticleRequest.getRecLevel(), updateArticleRequest.getDuring(), updateArticleRequest.getDue()
                , updateArticleRequest.getContent(), updateArticleRequest.getProjectImg());

        return articleRepository.save(article);
    }

    //모집 글 삭제
    public void delete(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    // 프로젝트 지원하기
    public void applyToProject(Long userId, Long articleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: "));

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다: "));

        Member member = Member.builder()
                .memberStatus(MemberStatus.WAITING)
                .user(user)
                .project(article.getProject())
                .build();

        memberRepository.save(member);
    }

}
