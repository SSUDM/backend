package com.DM.DeveloperMatching.repository;

import com.DM.DeveloperMatching.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
