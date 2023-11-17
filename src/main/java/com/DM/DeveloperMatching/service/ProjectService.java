package com.DM.DeveloperMatching.service;

import com.DM.DeveloperMatching.domain.Member;
import com.DM.DeveloperMatching.domain.Project;
import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.dto.Article.ArticleResponse;
import com.DM.DeveloperMatching.dto.Project.ProjectSummary;
import com.DM.DeveloperMatching.dto.Project.TeamMate;
import com.DM.DeveloperMatching.repository.ProjectRepository;
import com.DM.DeveloperMatching.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ProjectService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    //프로젝트 목록 전체 조회
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    //user가 한 프로젝트 목록 전체 조회
    public List<Project> findAllUserProjects(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("not found"));

        List<Project> projects = user.getUserInMember().stream()    //이 표현 자주 쓰임 잘 기억해 놓을 것
                .map(member -> member.getProject())
                .collect(Collectors.toList());

        return projects;
    }

    //user가 한 프로젝트 목록 요약 정보 추출
    public List<ProjectSummary> extractSummary(Long userId) {
        List<Project> projects = findAllUserProjects(userId);

        List<ProjectSummary> projectSummaries = projects.stream()
                .map(project -> {
                    ArticleResponse article = new ArticleResponse(project.getArticle());
                    ProjectSummary summary = new ProjectSummary(article);
                    return summary;
                })
                .collect(Collectors.toList());

        return projectSummaries;
    }
    //프로젝트 참여 중인 팀원 조회
    public List<TeamMate> getTeamMates(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("projectId에 해당하는 프로젝트를 찾을 수 없습니다: " + projectId));

        List<Member> teamMembers = project.getProjectInMember();

        List<TeamMate> teamMates = teamMembers.stream()
                .map(member -> new TeamMate(member.getUser().getUserName()))
                .collect(Collectors.toList());

        return teamMates;
    }


    //프로젝트 요청한 인원 조회


    //인기 프로젝트 목록


}
