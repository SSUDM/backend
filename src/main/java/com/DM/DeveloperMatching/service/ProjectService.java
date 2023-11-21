package com.DM.DeveloperMatching.service;

import com.DM.DeveloperMatching.domain.*;
import com.DM.DeveloperMatching.dto.Article.ArticleResponse;
import com.DM.DeveloperMatching.dto.Project.ProjectResponse;
import com.DM.DeveloperMatching.dto.Project.ProjectSummary;
import com.DM.DeveloperMatching.dto.Project.TeamMate;
import com.DM.DeveloperMatching.dto.User.UserRequestDto;
import com.DM.DeveloperMatching.repository.MemberRepository;
import com.DM.DeveloperMatching.repository.ProjectRepository;
import com.DM.DeveloperMatching.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ProjectService {
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    //프로젝트 목록 전체 조회
    public List<Project> findAllProjects() {
        return projectRepository.findAll();
    }

    //인기 프로젝트 목록
    public List<ProjectResponse> getPopularProjects() {
        List<Project> projects = projectRepository.findAll();

        // 좋아요 수를 기준으로 내림차순으로 프로젝트 정렬
        projects.sort(Comparator.comparingInt(Project::getLikes).reversed());

        // 프로젝트를 ProjectResponse 객체로 매핑
        List<ProjectResponse> popularProjects = projects.stream()
                .map(project -> new ProjectResponse(project))
                .collect(Collectors.toList());

        return popularProjects;
    }

    //user가 참여한 프로젝트 목록 찾아서 가져오기(아래에서 dto화 해서 반환)
    public List<Project> findAllUserProjects(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("not found"));

        List<Project> projects = user.getUserInMember().stream()    //이 표현 자주 쓰임 잘 기억해 놓을 것
                .map(member -> member.getProject())
                .collect(Collectors.toList());

        return projects;
    }

    //user가 참여한 프로젝트 목록 전체 조회
    public List<ProjectResponse> getAllUserProjects(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("not found"));
        List<Project> projects = findAllUserProjects(userId);

        List<ProjectResponse> projectResponses = projects.stream()
                .map(project -> {
                    ProjectResponse responses = new ProjectResponse(project);
                    return responses;
                })
                .collect(Collectors.toList());

        return projectResponses;
    }

    //user가 참여한 프로젝트 목록 요약 정보 추출
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

    //프로젝트 지원
    public void applyToProject(Long userId, Long projectId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: "));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다: "));

        Member member = Member.builder()
                .memberStatus(MemberStatus.WAITING)
                .user(user)
                .project(project)
                .build();

        memberRepository.save(member);
    }

    //프로젝트 종료
    public void terminateProject(Long pId) {
        Project project = projectRepository.findById(pId)
                .orElseThrow(() -> new IllegalArgumentException("not found project"));

        project.updateProjectStatus(ProjectStatus.DONE);
        projectRepository.save(project);
    }

    //프로젝트 참여 중인 팀원 조회
    public List<TeamMate> getTeamMates(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("projectId에 해당하는 프로젝트를 찾을 수 없습니다: " ));

        List<Member> teamMembers = project.getProjectInMember();

        List<TeamMate> teamMates = teamMembers.stream()
                .map(member -> new TeamMate(member.getUser().getUserName()))
                .collect(Collectors.toList());

        return teamMates;
    }

    //프로젝트 지원한 인원 조회
    public List<UserRequestDto> getAppliedUsers(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException(""));

        List<Member> members = project.getProjectInMember();

        List<UserRequestDto> appliedUsers = members.stream()
                .filter(member -> member.getMemberStatus() == MemberStatus.WAITING) // 상태가 "waiting"인 멤버만 필터링
                .map(member -> new UserRequestDto(member.getUser().getUserName()))
                .collect(Collectors.toList());

        return appliedUsers;
    }

    //프로젝트 참가 요청 수락
    public String acceptJoinProject(Long userId, Long projectId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: "));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다: "));

        Member member = (Member) memberRepository.findByUserAndProject(user, project)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자의 프로젝트 멤버를 찾을 수 없습니다."));

        if(member.getMemberStatus()==MemberStatus.ACCEPTED){
            return "이미 참가한 인원입니다.";
        }
        else if(member.getMemberStatus()==MemberStatus.WAITING){
            member.update(MemberStatus.ACCEPTED);
            memberRepository.save(member);
            return "프로젝트 참가 요청을 수락했습니다.";
        }
        else {
            return "지원하지 않았거나 이미 거절된 인원입니다.";
        }
    }

    // 프로젝트 참가 요청 거절
    public String rejectJoinProject(Long userId, Long projectId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: "));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("프로젝트를 찾을 수 없습니다: "));

        Member member = (Member) memberRepository.findByUserAndProject(user, project)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자의 프로젝트 멤버를 찾을 수 없습니다."));
        if(member.getMemberStatus()==MemberStatus.ACCEPTED){
            return "이미 참가한 인원입니다.";
        }
        else if(member.getMemberStatus()==MemberStatus.WAITING){
            member.update(MemberStatus.REJECTED);
            memberRepository.delete(member);
            return "프로젝트 참가 요청을 거절했습니다.";
        }
        else {
            return "지원하지 않았거나 이미 거절된 인원입니다.";
        }
    }

    /*//협업 요청 수락
    public String acceptSuggestProject(Long userId, Long projectId) {

    }

    //협업 요청 거절
    public String rejectSuggestProject(Long userId, Long projectId) {

    }*/


}