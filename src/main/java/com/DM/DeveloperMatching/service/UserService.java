package com.DM.DeveloperMatching.service;

import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.dto.User.UserInfoResponse;
import com.DM.DeveloperMatching.dto.User.UserRequestDto;
import com.DM.DeveloperMatching.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProjectService projectService;

    //내 정보 가져오기
    public UserInfoResponse getUserInfo(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("not found user"));

        UserInfoResponse userInfoResponse = new UserInfoResponse(user, projectService);

        return userInfoResponse;
    }

    //이력서 저장
    public User saveResume(UserRequestDto userRequestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found user"));

        user.updateResume(userRequestDto.getUserName(), userRequestDto.getPart(), userRequestDto.getLevel(),
                userRequestDto.getIntroduction(), userRequestDto.getTech(), userRequestDto.getCareer());

        return userRepository.save(user);
    }

    //이력서 조회
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found user"));
    }
}