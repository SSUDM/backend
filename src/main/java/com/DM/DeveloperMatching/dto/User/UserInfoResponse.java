package com.DM.DeveloperMatching.dto.User;

import com.DM.DeveloperMatching.domain.Level;
import com.DM.DeveloperMatching.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserInfoResponse {
    private String userName;
    private String part;
    private Level level;
    private Double point;
    private String introduction;
    private String tech;
    private String career;

    //내 프로젝트 추가

    public UserInfoResponse(User user) {
        this.userName = user.getUserName();
        this.part = user.getPart();
        this.level = user.getLevel();
        this.point = user.getPoint();
        this.introduction = user.getIntroduction();
        this.tech = user.getTech();
        this.career = user.getCareer();
    }
}
