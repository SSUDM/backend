package com.DM.DeveloperMatching.dto.Recommend;

import com.DM.DeveloperMatching.domain.Article;
import com.DM.DeveloperMatching.domain.Level;
import com.DM.DeveloperMatching.domain.User;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RecommendUserDto {
    private String userName;
    private String email;
    private String part;
    private List<String> tech;
    private Level level;
    private Double point;
    private String introduction;
    @Lob
    private Byte[] userImg;

    public static RecommendUserDto toDto(User user) {
        RecommendUserDto recommendDto = new RecommendUserDto();
        recommendDto.userName = user.getUserName();
        recommendDto.email = user.getEmail();
        recommendDto.part = user.getPart();
        recommendDto.tech = Arrays.asList(user.getTech().split(", \\s*"));
        recommendDto.level = user.getLevel();
        recommendDto.point = user.getPoint();
        recommendDto.introduction = user.getIntroduction();
        recommendDto.userImg = user.getUserImg();
        return recommendDto;
    }
}
