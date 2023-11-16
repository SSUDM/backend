package com.DM.DeveloperMatching.controller;

import com.DM.DeveloperMatching.config.jwt.JwtTokenUtils;
import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.dto.Email.EmailMessage;
import com.DM.DeveloperMatching.dto.Email.EmailRequest;
import com.DM.DeveloperMatching.dto.Login.LoginRequest;
import com.DM.DeveloperMatching.dto.Register.RegisterRequest;
import com.DM.DeveloperMatching.service.MailService;
import com.DM.DeveloperMatching.service.RegisterAndLoginService;
import com.DM.DeveloperMatching.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class RegisterController {

    private final UserService userService;
    private final RegisterAndLoginService registerAndLoginService;
    private final MailService mailService;

    @PostMapping("/register/check-nickname")
    public ResponseEntity<String> registerNickName(@RequestBody RegisterRequest registerRequest) {
        if (registerAndLoginService.checkNickNameDuplicate(registerRequest.getNickName())) {
            return ResponseEntity.badRequest().body("중복되는 별명이 존재합니다.");
        }
        else return ResponseEntity.ok().body("사용 가능한 별명입니다.");
    }

    @PostMapping("/register/check-email")
    public ResponseEntity<String> registerEmail(@RequestBody RegisterRequest registerRequest) {
        if (registerAndLoginService.checkLoginEmailDuplicate(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body("중복되는 Email이 존재합니다.");
        }
        else return ResponseEntity.ok().body("사용 가능한 이메일입니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        User loginUser = registerAndLoginService.login(loginRequest);
//        System.out.println("loginUser: " + loginUser);

        //로그인 email이나 password가 틀린 경우 global error return
        if (loginUser == null) {
            return ResponseEntity.badRequest().body("로그인 Email 또는 설정한 비밀번호가 틀렸습니다.");
        }

        //로그인을 성공했으면 이제 Jwt 토큰을 발급해준다.
        //시크릿 키 들어갈 자리
        String secretKey = " ";
        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분
        String jwtToken = JwtTokenUtils.createToken(loginUser.getUId(),loginUser.getEmail(), secretKey,expireTimeMs);
//        System.out.println("jwtToken = " + jwtToken);
        return ResponseEntity.ok().body(jwtToken);
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        boolean isNicknameDuplicate = registerAndLoginService.checkNickNameDuplicate(registerRequest.getNickName());
        boolean isEmailDuplicate = registerAndLoginService.checkLoginEmailDuplicate(registerRequest.getEmail());
        if (isEmailDuplicate || isNicknameDuplicate) {
            StringBuilder errorMessage = new StringBuilder();
            if (isEmailDuplicate) {
                errorMessage.append("중복되는 Email이 존재합니다.");
            }
            if (isNicknameDuplicate) {
                errorMessage.append("중복되는 별명이 존재합니다.");
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        registerAndLoginService.register1(registerRequest);
        return ResponseEntity.ok().body("회원가입을 성공하였습니다.");
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEMail(@RequestBody EmailRequest emailRequest) {
        int authNumber = mailService.sendMail(emailRequest.getEmail());
        String number = "" + authNumber;

        return ResponseEntity.ok().body(number);
    }
}
