package com.DM.DeveloperMatching.dto.Article;

import com.DM.DeveloperMatching.domain.Article;
import com.DM.DeveloperMatching.domain.Level;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ArticleResponse {
    private Long articleOwnerId;
    private String title;
    private int maximumMember;
    private String recPart;
    private String recTech;
    private Level recLevel;
    private String during;
    private Date due;
    private String content;
    @Lob
    private Byte[] projectImg;

    public ArticleResponse(Article article) {
        this.articleOwnerId = article.getArticleOwner().getUId();
        this.title = article.getTitle();
        this.maximumMember = article.getMaximumMember();
        this.recPart = article.getRecPart();
        this.recTech = article.getRecTech();
        this.recLevel = article.getRecLevel();
        this.during = article.getDuring();
        this.due = article.getDue();
        this.content = article.getContent();
        this.projectImg = article.getProjectImg();
    }
}
