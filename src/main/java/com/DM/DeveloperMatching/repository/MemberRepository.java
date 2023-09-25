package com.DM.DeveloperMatching.repository;

import com.DM.DeveloperMatching.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
