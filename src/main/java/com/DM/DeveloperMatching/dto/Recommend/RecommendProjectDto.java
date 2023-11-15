package com.DM.DeveloperMatching.dto.Recommend;

import com.DM.DeveloperMatching.domain.Article;
import com.DM.DeveloperMatching.domain.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RecommendProjectDto {
    private String title;
    private String recPart;
    private String recTech;
    private Level recLevel;

    public static RecommendProjectDto toDto(Article article) {
        RecommendProjectDto recommendDto = new RecommendProjectDto();
        recommendDto.title = article.getTitle();
        recommendDto.recPart = article.getRecPart();
        recommendDto.recTech = article.getRecTech();
        recommendDto.recLevel = article.getRecLevel();
        return recommendDto;
    }
}
