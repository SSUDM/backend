package com.DM.DeveloperMatching.dto.Recommend;

import com.DM.DeveloperMatching.domain.Article;
import com.DM.DeveloperMatching.domain.Level;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RecommendProjectDto {
    private Long aId;
    private String title;
    private List<String> recPart;
    private List<String> recTech;
    private Level recLevel;
    @Lob
    private Byte[] projectImg;

    public static RecommendProjectDto toDto(Article article) {
        RecommendProjectDto recommendDto = new RecommendProjectDto();
        recommendDto.aId = article.getAId();
        recommendDto.title = article.getTitle();
        recommendDto.recPart = Arrays.asList(article.getRecPart().split(", \\s*"));
        recommendDto.recTech = Arrays.asList(article.getRecTech().split(", \\s*"));
        recommendDto.recLevel = article.getRecLevel();
        recommendDto.projectImg = article.getProjectImg();
        return recommendDto;
    }
}