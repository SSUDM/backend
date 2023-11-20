package com.DM.DeveloperMatching.repository;

import com.DM.DeveloperMatching.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByPart(String part);
}