package com.DM.DeveloperMatching.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long pId;

    @Column(name = "member_count")
    private int memberCnt;

    @OneToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_status")
    private ProjectStatus projectStatus;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL) //양방향 잡을라고
    private List<Member> projectInMember = new ArrayList<>();

    @OneToOne
    @Column(name = "review")
    private Review review;

    @Builder
    public Project(int memberCnt,Article article,ProjectStatus projectStatus) {
        this.memberCnt = memberCnt;
        this.article = article;
        this.projectStatus = projectStatus;
    }


}
