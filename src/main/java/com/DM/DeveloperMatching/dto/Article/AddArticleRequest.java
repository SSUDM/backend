package com.DM.DeveloperMatching.dto.Article;

import com.DM.DeveloperMatching.domain.Article;
import com.DM.DeveloperMatching.domain.Level;
import com.DM.DeveloperMatching.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddArticleRequest {

    private User articleOwner;
    private String title;
    private String recPart;
    private int maximumMember;
    private String recTech;
    private Date during;
    private Date due;
    private Level recLevel;
    private String content;
    @Lob
    private Byte[] projectImg;

    public Article toEntity(User user) {
        return Article.builder()
                .articleOwner(user)
                .title(title)
                .recPart(recPart)
                .maximumMember(maximumMember)
                .recTech(recTech)
                .during(during)
                .due(due)
                .recLevel(recLevel)
                .content(content)
                .build();
    }

}
