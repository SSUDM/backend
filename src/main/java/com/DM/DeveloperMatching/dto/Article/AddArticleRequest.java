package com.DM.DeveloperMatching.dto.Article;

import com.DM.DeveloperMatching.domain.Article;
import com.DM.DeveloperMatching.domain.Level;
import com.DM.DeveloperMatching.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddArticleRequest {

    private User articleOwner;
    private String title;
    private int maximumMember;
    private String recPart;
    private String recTech;
    private Level recLevel;
    private Date during;
    private Date due;
    private String content;
    @Lob
    private Byte[] projectImg;

    public Article toEntity(User user) {
        return Article.builder()
                .articleOwner(user)
                .title(title)
                .maximumMember(maximumMember)
                .recPart(recPart)
                .recTech(recTech)
                .during(during)
                .due(due)
                .recLevel(recLevel)
                .content(content)
                .build();
    }
}
