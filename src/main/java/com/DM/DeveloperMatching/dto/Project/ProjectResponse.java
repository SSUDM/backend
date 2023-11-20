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
    private int memberCnt;
    private ProjectStatus projectStatus;
    private int likes;

    public ProjectResponse(Project project) {
        this.pId = project.getPId();
        this.memberCnt = project.getMemberCnt();
        this.projectStatus = project.getProjectStatus();
        this.likes = project.getLikes();
    }
}
