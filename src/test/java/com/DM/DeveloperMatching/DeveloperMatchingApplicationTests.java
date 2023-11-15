package com.DM.DeveloperMatching;

import com.DM.DeveloperMatching.domain.Article;
import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.repository.ArticleRepository;
import com.DM.DeveloperMatching.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
@SpringBootTest
class DeveloperMatchingApplicationTests {
//	@Autowired
//	UserRepository userRepository;
//	@Autowired
//	ArticleRepository articleRepository;
//	@Test
//	void contextLoads() {
//		Long userId = 1L;
//		Long aId = 2L;
//
//		User user = userRepository.findById(userId)
//				.orElseThrow(EntityNotFoundException::new);
//		Article article1 = articleRepository.findById(userId)
//				.orElseThrow(EntityNotFoundException::new);
//		Article article2 = articleRepository.findById(aId)
//				.orElseThrow(EntityNotFoundException::new);
//		List<Article> articles = new ArrayList<>();
//		articles.add(article1);
//		articles.add(article2);
//		user.exam(articles);
//	}
}
