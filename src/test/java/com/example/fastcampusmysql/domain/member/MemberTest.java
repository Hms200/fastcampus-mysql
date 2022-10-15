package com.example.fastcampusmysql.domain.member;

import com.example.fastcampusmysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class MemberTest {

    @DisplayName("회원은 닉네임을 변경할 수 있다.")
    @Test
    void testChangeNickname() {
        var member = MemberFixtureFactory.create();
        var newNickname = "Nickname";

        member.changeNickname(newNickname);

        Assertions.assertEquals(newNickname, member.getNickname());

    }

    @DisplayName("회원의 넥네임은 10자를 초과할 수 없다.")
    @Test
    void testNicknameMaxLength() {
        var member = MemberFixtureFactory.create();
        var newNickname = "newNickname";

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> member.changeNickname(newNickname));
    }

}
