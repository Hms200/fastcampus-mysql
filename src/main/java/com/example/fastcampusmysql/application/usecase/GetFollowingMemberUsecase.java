package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetFollowingMemberUsecase {

    private final MemberReadService memberReadService;

    private final FollowReadService followReadService;

    public List<MemberDto> excute(Long memberId) {

        var followings = followReadService.getFollowers(memberId);

        return memberReadService.getMembers(followings.stream().map(Follow::getToMemberId).toList());
    }

}