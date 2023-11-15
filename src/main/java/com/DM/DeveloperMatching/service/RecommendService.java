package com.DM.DeveloperMatching.service;

import com.DM.DeveloperMatching.domain.Article;
import com.DM.DeveloperMatching.domain.Level;
import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.repository.ArticleRepository;
import com.DM.DeveloperMatching.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.DM.DeveloperMatching.domain.Level.MASTER;
import static com.DM.DeveloperMatching.domain.Level.SENIOR;

@RequiredArgsConstructor
@Service
@Transactional
public class RecommendService {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    @PersistenceContext
    private final EntityManager entityManager;

    public List<Article> recommendProjectByCS(Long uId) {
        User user = userRepository.findById(uId).
                orElseThrow(EntityNotFoundException::new);
        List<Article> projects = articleRepository.findAll();

        Map<Article, Double> smap = new HashMap<>();
        for(Article p : projects) {
            smap.put(p, cosineSimilarity(user, p));
        }
        List<Map.Entry<Article, Double>> entryList = new ArrayList<Map.Entry<Article, Double>>(smap.entrySet());
        Collections.sort(entryList, (obj1, obj2) -> obj2.getValue().compareTo(obj1.getValue()));
        List<Article> result = new ArrayList<>();
        for(Map.Entry<Article, Double> entry : entryList) {
            result.add(entry.getKey());
        }

        return result;
    }

    public List<Article> recommendProjectByCS(Long uId, List<String> recPart, List<String> recTech, List<String> recLevel) {
        User user = userRepository.findById(uId).
                orElseThrow(EntityNotFoundException::new);
        List<Article> projects = new ArrayList<>();

        if(!(recPart.isEmpty()) && recTech.isEmpty() && recLevel.isEmpty()) {
            projects = articleRepository.findAllByRecPart(recPart);
        }
        else if(recPart.isEmpty() && !(recTech.isEmpty()) && recLevel.isEmpty()) {
            String queryString = makeTechQuery(recTech);
            Query query = entityManager.createQuery(queryString, Article.class);
            projects = query.getResultList();
        }
        else if(recPart.isEmpty() && recTech.isEmpty() && !(recLevel.isEmpty())) {
            List<Level> recLevels = recLevel.stream()
                    .map(String::toUpperCase)
                    .map(Level::valueOf)
                    .collect(Collectors.toList());
            projects = articleRepository.findAllByRecLevel(recLevels);
        }
        else if(!(recPart.isEmpty()) && !(recTech.isEmpty()) && recLevel.isEmpty()) {
            String queryString = makeTechQuery(recTech) + makePartQuery(recPart);
            Query query = entityManager.createQuery(queryString, Article.class);
            projects = query.getResultList();
        }
        else if(!(recPart.isEmpty()) && recTech.isEmpty() && !(recLevel.isEmpty())) {
            List<Level> recLevels = recLevel.stream()
                    .map(String::toUpperCase)
                    .map(Level::valueOf)
                    .collect(Collectors.toList());
            projects = articleRepository.findByPartAndLevel(recPart, recLevels);
        }
        else if(recPart.isEmpty() && !(recTech.isEmpty()) && !(recLevel.isEmpty())) {
            String queryString = makeTechQuery(recTech) + makeLevelQuery(recLevel);
            Query query = entityManager.createQuery(queryString, Article.class);
            projects = query.getResultList();
        }
        else {
            String queryString = makeTechQuery(recTech) + makeLevelQuery(recLevel) + makePartQuery(recPart);
            Query query = entityManager.createQuery(queryString, Article.class);
            projects = query.getResultList();
        }
        Map<Article, Double> smap = new HashMap<>();
        for(Article p : projects) {
            smap.put(p, cosineSimilarity(user, p));
        }
        List<Map.Entry<Article, Double>> entryList = new ArrayList<Map.Entry<Article, Double>>(smap.entrySet());
        Collections.sort(entryList, (obj1, obj2) -> obj2.getValue().compareTo(obj1.getValue()));
        List<Article> result = new ArrayList<>();
        for(Map.Entry<Article, Double> entry : entryList) {
            result.add(entry.getKey());
        }

        return result;
    }

    public List<User> recommendUserByCS(Long uId) {
        User user = userRepository.findById(uId)
                .orElseThrow(EntityNotFoundException::new);

        List<Article> articles = user.getArticles();

        Collections.sort(articles, Comparator.comparing(Article::getDue));
        for(Article a : articles) {
            System.out.println(a.getTitle());
        }
        List<User> users = userRepository.findAll();

        List<User> result = new ArrayList<>();

        for (Article a : articles) {
            Map<User, Double> smap = new HashMap<>();

            for (User u : users) {
                double similarity = cosineSimilarity(u, a);
                smap.put(u, similarity);
            }

            List<Map.Entry<User, Double>> entryList = new ArrayList<>(smap.entrySet());
            entryList.sort((obj1, obj2) -> obj2.getValue().compareTo(obj1.getValue()));

            for (Map.Entry<User, Double> entry : entryList) {
                result.add(entry.getKey());
            }
        }

        return result;
    }

    private String makeTechQuery(List<String> recTech) {
        String queryString = "SELECT a FROM Article a WHERE " +
                recTech.stream()
                        .map(tech -> "a.recTech LIKE '%" + tech + "%'")
                        .collect(Collectors.joining(" AND "));

        return queryString;
    }

    private String makePartQuery(List<String> recPart) {
        String inQueries = recPart.stream()
                .map(part -> "'" + part.replace("'", "''") + "'") // 문자열은 싱글 쿼트로 감싸야 함
                .collect(Collectors.joining(", "));

        return " AND a.recPart IN (" + inQueries + ")";
    }

    private String makeLevelQuery(List<String> recLevel) {
        String inQueries = recLevel.stream()
                .map(part -> "'" + part.replace("'", "''") + "'") // 문자열은 싱글 쿼트로 감싸야 함
                .collect(Collectors.joining(", "));

        return " AND a.recLevel IN (" + inQueries + ")";
    }

    public int vectorP(String part) {
        if(part.equals("BackEnd")) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public int vectorL(Level level) {
        if(level == MASTER) {
            return 2;
        } else if(level == SENIOR) {
            return 1;
        } else {
            return 0;
        }
    }

    public List<Integer> vectorT(String tech) {
        final String[] techs = {"Spring Boot", "Spring", "React", "Vue.js", "Jpa", "Django", "Html", "Css", "Kotlin", "Java Script"};
        Map<String, Integer> vectors = new HashMap<>();
        for (String t : techs) {
            vectors.put(t, tech.contains(t) ? 1 : 0);
        }
        return new ArrayList<Integer>(vectors.values());
    }

    public double cosineSimilarity(User user, Article article) {
        List<Integer> vectorC = vectorT(user.getTech());
        List<Integer> vectorD = vectorT(article.getRecTech());
        vectorC.add(vectorP(user.getPart()));
        vectorC.add(vectorL(user.getLevel()));
        vectorD.add(vectorP(article.getRecPart()));
        vectorD.add(vectorL(article.getRecLevel()));
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;

        for (int i = 0; i < vectorC.size(); i++) {
            dotProduct += vectorC.get(i) * vectorD.get(i);
            normA += Math.pow(vectorC.get(i), 2);
            normB += Math.pow(vectorD.get(i), 2);
        }

        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
