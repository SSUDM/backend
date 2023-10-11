package com.DM.DeveloperMatching.service;

import com.DM.DeveloperMatching.repository.ArticleRepository;
import com.DM.DeveloperMatching.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ArticleRepository articleRepository;

    /*public Project save(AddProjectRequest projectRequest, Long aId) {
        Article article = articleRepository.findById(aId)
                .orElseThrow(() -> new IllegalArgumentException("not found article"));

        return projectRepository.save(projectRequest.toEntity());
    }*/
}
