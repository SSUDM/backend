package com.DM.DeveloperMatching.dto.Project;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TeamMate {
    private String userName;
    private String introduction;

    public TeamMate(String userName) {
        this.userName = userName;
    }
}