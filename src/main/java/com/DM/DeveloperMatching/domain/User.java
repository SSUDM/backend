package com.DM.DeveloperMatching.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 자동으로 1씩 증가함
    @Column(name = "user_id")
    private Long uId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "nickname", nullable = false)
    private String nickName;


    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "part")
    private String part;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;

    @Lob
    @Column(name = "user_img")
    private Byte[] userImg;

    @Column(name = "point")
    private Double point; //double은 null타입을 가질 수 없으니까 double로..

    @Column(name = "introduction", length = 100)
    private String introduction;

    @Column(name = "tech")
    private String tech;

//    @Column(name = "career")
//    private String career;

    @OneToMany(mappedBy = "articleOwner")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)   //양방향 잡을라고
    private List<Member> userInMember = new ArrayList<>();

    @OneToMany(mappedBy = "likesUser", cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Career> careerList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<History> history = new ArrayList<>();

    @Builder
    public User(String userName, String nickName,String email, String password, String phoneNum, String part, Level level, Double point,
                String introduction, String tech, List<Career> careerList, List<History> history) {
        this.userName = userName;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.phoneNum = phoneNum;
        this.part = part;
        this.level = level;
        this.point = point;
        this.introduction = introduction;
        this.tech = tech;
//        this.career = career;
        this.careerList = careerList;
        this.history = history;
    }

    public void updateResume(String userName, String part, Level level, String introduction, String tech, List<Career> careerList,
                             List<History> history) {
        this.userName = userName;
        this.part = part;
        this.level = level;
        this.introduction = introduction;
        this.tech = tech;
        this.careerList.addAll(careerList);
        this.history.addAll(history);
    }

    public void deleteCareer(String content) {
        Career delete = new Career();
        for(Career c : this.careerList) {
            if(c.getContent().equals(content)) {
                delete = c;
            }
        }
        this.careerList.remove(delete);
    }

    public void deleteHistory(String title) {
        History project = new History();
        for(History p : this.history) {
            if(p.getTitle().equals(title)) {
                project = p;
            }
        }
        this.history.remove(project);
    }
}