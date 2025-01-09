package com.ll.rest.domain.member.member.repository;

import com.ll.rest.domain.member.member.entity.Member;
import com.ll.rest.domain.post.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    String username(String username);
}