package com.DM.DeveloperMatching.repository;

import com.DM.DeveloperMatching.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
