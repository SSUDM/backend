package com.DM.DeveloperMatching.dto.Article;

import com.DM.DeveloperMatching.domain.Level;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateArticleRequest {

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

}
