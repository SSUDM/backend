package com.DM.DeveloperMatching.service;

import com.DM.DeveloperMatching.domain.Career;
import com.DM.DeveloperMatching.domain.History;
import com.DM.DeveloperMatching.domain.User;
import com.DM.DeveloperMatching.dto.User.Resume.CareerDto;
import com.DM.DeveloperMatching.dto.User.Resume.HistoryDto;
import com.DM.DeveloperMatching.dto.User.UserRequestDto;
import com.DM.DeveloperMatching.repository.CareerRepository;
import com.DM.DeveloperMatching.repository.HistoryRepository;
import com.DM.DeveloperMatching.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final CareerRepository careerRepository;
    private final HistoryRepository previousProjectRepository;
    public User saveResume(UserRequestDto userRequestDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found user"));
        String result = String.join(", ", userRequestDto.getTech());

        checkDuplicate(user, userRequestDto);

        List<Career> careerList = userRequestDto.getCareerList().stream()
                .filter(c -> !careerRepository.existsByUserAndContent(user, c.getCareer()))
                .map(c -> c.toEntity(user))
                .collect(Collectors.toList());
        List<History> history = userRequestDto.getHistory().stream()
                .filter(c -> !previousProjectRepository.existsByUserAndTitle(user, c.getTitle()))
                .map(c -> c.toEntity(user))
                .collect(Collectors.toList());
        user.updateResume(userRequestDto.getUserName(), userRequestDto.getPart(), userRequestDto.getLevel(),
                userRequestDto.getIntroduction(), result, careerList, history);

        return userRepository.save(user);
    }

    public void checkDuplicate(User user, UserRequestDto userRequestDto) {
        if(!user.getCareerList().isEmpty() && !user.getHistory().isEmpty()) {
            List<String> userC = user.getCareerList().stream()
                    .map(Career::getContent)
                    .collect(Collectors.toList());
            List<String> requestC = userRequestDto.getCareerList().stream()
                    .map(CareerDto::getCareer)
                    .collect(Collectors.toList());
            for(String s : userC) {
                if(!requestC.contains(s)) {
                    user.deleteCareer(s);
                    careerRepository.deleteByUserIdANDContent(user.getUId(), s);
                }
            }

            List<String> userH = user.getHistory().stream()
                    .map(History::getTitle)
                    .collect(Collectors.toList());
            List<String> requestH = userRequestDto.getHistory().stream()
                    .map(HistoryDto::getTitle)
                    .collect(Collectors.toList());
            for(String s : userH) {
                if(!requestH.contains(s)) {
                    user.deleteHistory(s);
                    previousProjectRepository.deleteByUserIdANDTitle(user.getUId(), s);
                }
            }
        }
    }

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found user"));
    }
}
