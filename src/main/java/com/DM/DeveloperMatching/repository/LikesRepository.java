package com.DM.DeveloperMatching.repository;

import com.DM.DeveloperMatching.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {

}