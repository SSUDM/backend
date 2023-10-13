package com.DM.DeveloperMatching.dto.User;

import com.DM.DeveloperMatching.domain.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRequestDto {
    private String userName;
    private String part;
    private Level level;
    private String introduction;
    private String tech;
    private String career;
}