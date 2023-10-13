package com.DM.DeveloperMatching.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne//(fetch = FetchType.LAZY) <= 이거 하면 article 조회할 때 오류남 why? 직렬화 문제 발생 proxy
    @JoinColumn(name = "user_id")
    private User articleOwner;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "maximum_member")
    private int maximumMember;

    @Column(name = "recruit_part")
    private String recPart; //list로 해야하지만 List<String> 사용 불가 table이나 class를 하나 더 만들어야할듯

    @Column(name = "recruit_tech")
    private String recTech; //list로 해야하지만 List<String> 사용 불가 table이나 class를 하나 더 만들어야할듯

    @Enumerated(EnumType.STRING)
    @Column(name = "recruit_level")
    private Level recLevel;

    @Temporal(TemporalType.DATE)
    private Date during;

    @Temporal(TemporalType.DATE)
    private Date due;

    @Lob
    @Column(name = "project_image")
    private Byte[] projectImg;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project;

    public void update(String title, int maximumMember, String recPart, String recTech, Level recLevel,
                       Date during, Date due, String content, Byte[] projectImg) {
        this.title = title;
        this.maximumMember = maximumMember;
        this.recPart = recPart;
        this.recTech = recTech;
        this.recLevel = recLevel;
        this.during = during;
        this.due = due;
        this.content = content;
        this.projectImg = projectImg;
    }

    @Builder
    public Article(User articleOwner, String title, int maximumMember, String recPart, String recTech, Level recLevel,
                   Date during, Date due, String content, Byte[] projectImg) {
        this.articleOwner = articleOwner;
        this.title = title;
        this.maximumMember = maximumMember;
        this.recPart = recPart;
        this.recTech = recTech;
        this.recLevel = recLevel;
        this.during = during;
        this.due = due;
        this.content = content;
        this.projectImg = projectImg;
        this.project = Project.builder() // Article을 생성할 때 자동으로 Project도 생성
                .memberCnt(1) // Project의 memberCnt를 Article의 maximumMember와 일치시킴
                .projectStatus(ProjectStatus.RECRUITING) // 적절한 ProjectStatus 설정
                .build();
    }

}
