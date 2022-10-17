package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usecase.CreateFollowMemberUsecase;
import com.example.fastcampusmysql.application.usecase.GetFollowingMemberUsecase;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {

    private final CreateFollowMemberUsecase createFollowMemberUsecase;
    private final GetFollowingMemberUsecase getFollowingMemberUsecase;

    @PostMapping("/{fromMemberId}/{toMemberId}")
    public void register(@PathVariable Long fromMemberId, @PathVariable Long toMemberId) {
        createFollowMemberUsecase.execute(fromMemberId, toMemberId);
    }

    @GetMapping("members/{fromMemberId}")
    public List<MemberDto> getFollowingMember(@PathVariable Long fromMemberId) {
        return getFollowingMemberUsecase.excute(fromMemberId);
    }

}
