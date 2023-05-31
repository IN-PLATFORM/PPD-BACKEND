package com.platform.ppdbackend.domain.repository;


import com.platform.ppdbackend.domain.user.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
}

