package com.example.demo.member;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberDao extends JpaRepository<Member, Long> {
    Member findByUsername(String username);
}
