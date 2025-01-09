package com.ll.rest.global.baseInit;

import com.ll.rest.domain.member.member.entity.Member;
import com.ll.rest.domain.member.member.service.MemberService;
import com.ll.rest.domain.post.post.entity.Post;
import com.ll.rest.domain.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor

public class BaseInitData {
    private final MemberService memberService;
    private final PostService postService;

    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner baseInitDataApplicationRunner() {
        return args -> {
            self.work1();
            self.work2();
        };
    }

    @Transactional
    public void work1() {
        if (memberService.count() > 0) return;

        Member memberSystem = memberService.join("system", "1234", "시스템");
        Member memberAdmin = memberService.join("admin", "1234", "관리자");
        Member memberUser1 = memberService.join("user1", "1234", "유저1");
        Member memberUser2 = memberService.join("user2", "1234", "유저2");
        Member memberUser3 = memberService.join("user3", "1234", "유저3");
    }

    @Transactional
    public void work2() {
        if (postService.count() > 0) return;

        Member memberUser1 = memberService.findByUsername("user1").get();
        Member memberUser2 = memberService.findByUsername("user2").get();

        Post post1 = postService.write(memberUser1, "축구 하실 분?", "14시 까지 22명을 모아야 합니다.");
        Post post2 = postService.write(memberUser1,"배구 하실 분?", "15시 까지 12명을 모아야 합니다.");
        Post post3 = postService.write(memberUser2,"농구 하실 분?", "16시 까지 10명을 모아야 합니다.");
        //n+1 발생할때 default_batch_fetch_size: 100 넣어주면 끝

        // default_batch_fetch_size 를 통해서 N + 1 문제를 해결할 수 있다.

        //이 설정은 콜렉션(리스트)안에 있는 엔티티 객체를 LAZY 로딩하기 위해서 쿼리를 실행할 때

        //한번에 설정한 만큼의 객체를 SELECT 해서 가져올 수 있게 한다.

        //기존의 SELECT를 많이 해서 하나씩 가져오는 것 보다 훨씬 효율적이다.
    }
}