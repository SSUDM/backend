package com.DM.DeveloperMatching.service;

import com.DM.DeveloperMatching.domain.Project;
import com.DM.DeveloperMatching.repository.ArticleRepository;
import com.DM.DeveloperMatching.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    //프로젝트 목록 전체 조회
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }
}
