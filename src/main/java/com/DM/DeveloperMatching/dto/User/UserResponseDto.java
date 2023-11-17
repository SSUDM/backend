package com.DM.DeveloperMatching.dto.User;

import com.DM.DeveloperMatching.domain.Level;
import com.DM.DeveloperMatching.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserResponseDto {
    private String userName;
    private String part;
    private Level level;
    private String introduction;
    private List<String> tech;
    private String career;

    public UserResponseDto(User user) {
        this.userName = user.getUserName();
        this.part = user.getPart();
        this.level = user.getLevel();
        this.introduction = user.getIntroduction();
        this.tech = Arrays.asList(user.getTech().split(",\\s*"));
        this.career = user.getCareer();
    }
}
