package com.DM.DeveloperMatching.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long aId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User articleOwner;

    @Column(name = "title")
    private String title;

    @Column(name = "maximum_member")
    private int maximumMember;

    @Column(name = "recruit_part")
    private String recPart;

    @Column(name = "recruit_tech")
    private String recTech;

    @Enumerated(EnumType.STRING)
    @Column(name = "recruit_level")
    private Level recLevel;

    @Temporal(TemporalType.DATE)
    private Date during;

    @Temporal(TemporalType.DATE)
    private Date due;

    @Column(name = "project_information")
    private String projectInfo;

    @Lob
    @Column(name = "project_image")
    private Byte[] projectImg;


//    만약에 Article에서 Project가 필요하다면, 양방향 할거면
//    @OneToOne(mappedBy = "article",cascade = CascadeType.ALL)
//    private Project project;

    @Builder
    public Article(User articleOwner, String title, int maximumMember, String recPart, String recTech, Level recLevel,
                   Date during, Date due, String projectInfo, Byte[] projectImg) {
        this.articleOwner = articleOwner;
        this.title = title;
        this.maximumMember = maximumMember;
        this.recPart = recPart;
        this.recTech = recTech;
        this.recLevel = recLevel;
        this.during = during;
        this.due = due;
        this.projectInfo = projectInfo;
        this.projectImg = projectImg;
    }

}
