package com.DM.DeveloperMatching.dto.Project;

import com.DM.DeveloperMatching.domain.Project;
import com.DM.DeveloperMatching.domain.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProjectResponse {
    private Long pId;
    private Long aId;
    private String title;
    private Byte[] projectImg;
    private Integer memberCnt;
    private ProjectStatus projectStatus;
    private Integer likes;

    public ProjectResponse(Project project) {
        this.pId = project.getPId();
        this.aId = project.getArticle().getAId();
        this.title = project.getArticle().getTitle();
        this.projectImg = project.getArticle().getProjectImg();
        this.memberCnt = project.getMemberCnt();
        this.projectStatus = project.getProjectStatus();
        this.likes = project.getLikes();
    }
}
