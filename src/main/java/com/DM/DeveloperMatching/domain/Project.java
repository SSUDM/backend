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

    @Enumerated(EnumType.STRING)
    @Column(name = "project_status")
    private ProjectStatus projectStatus;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL) //양방향 잡을라고
    private List<Member> projectInMember = new ArrayList<>();

   /* @OneToOne(mappedBy = "project", cascade = CascadeType.ALL)    //Json 무한 루프 문제 발생
    private Article article;*/                                      //@JsonBackReference를 사용하면 Project에서
                                                                    //article 접근도 안됨

    @Builder
    public Project(int memberCnt, ProjectStatus projectStatus) {
        this.memberCnt = memberCnt;
        this.projectStatus = projectStatus;
    }


}
