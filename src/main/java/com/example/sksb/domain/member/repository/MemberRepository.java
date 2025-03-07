package com.example.sksb.domain.member.repository;

import com.example.sksb.domain.member.entity.Member;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);

    Optional<Member> findByRefreshToken(String refreshToken);
}
