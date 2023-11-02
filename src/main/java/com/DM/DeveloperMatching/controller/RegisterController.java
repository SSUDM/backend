package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.config.jwt.JwtTokenUtils;
import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.dto.Login.LoginRequest;
import com.DM.DeveloperMatching.dto.Register.RegisterRequest;
import com.DM.DeveloperMatching.service.RegisterAndLoginService;
import com.DM.DeveloperMatching.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class RegisterController {

    private final UserService userService;
    private final RegisterAndLoginService registerAndLoginService;

    //회원가입 등록,
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        //email, nickName 둘 다 중복 체크
        if (registerAndLoginService.checkLoginEmailDuplicate(registerRequest.getEmail()) && registerAndLoginService.checkNickNameDuplicate(registerRequest.getNickName())) {
            return ResponseEntity.badRequest().body("Email과 별명이 모두 중복됩니다.");
        }
        //email 중복 체크
        if (registerAndLoginService.checkLoginEmailDuplicate(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body("중복되는 Email이 존재합니다.");
        }

        //닉네임(nickName) 중복 체크
        if (registerAndLoginService.checkNickNameDuplicate(registerRequest.getNickName())) {
            return ResponseEntity.badRequest().body("중복되는 별명이 존재합니다.");
        }

        //password와 passwordCheck이 같은지 체크
        if (!registerRequest.getPassword().equals(registerRequest.getPasswordCheck())) {
            return ResponseEntity.badRequest().body("설정한 비밀번호가 일치하지 않습니다.");
        }

        registerAndLoginService.register1(registerRequest);
        return ResponseEntity.ok("회원가입을 성공하였습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        User loginUser = registerAndLoginService.login(loginRequest);
        System.out.println("loginUser: " + loginUser);

        //로그인 email이나 password가 틀린 경우 global error return
        if (loginUser == null) {
            return ResponseEntity.badRequest().body("로그인 Email 또는 설정한 비밀번호가 틀렸습니다.");
        }

        //로그인을 성공했으면 이제 Jwt 토큰을 발급해준다.
        //시크릿 키 들어갈 자리
        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분
        String jwtToken = JwtTokenUtils.createToken(loginUser.getUId(),loginUser.getEmail(), secretKey, expireTimeMs);
        System.out.println("jwtToken = " + jwtToken);
        return ResponseEntity.ok().body(jwtToken);
    }





}
