package com.DM.DeveloperMatching.service;

import com.DM.DeveloperMatching.domain.User;
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

    public User saveResume(UserRequestDto userRequestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found user"));

        user.updateResume(userRequestDto.getUserName(), userRequestDto.getPart(), userRequestDto.getLevel(),
                userRequestDto.getIntroduction(), userRequestDto.getTech(), userRequestDto.getCareer());

        return userRepository.save(user);
    }


    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found user"));
    }
}
