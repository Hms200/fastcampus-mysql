package com.example.fastcampusmysql.util;

import com.example.fastcampusmysql.domain.member.entity.Member;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

public class MemberFixtureFactory {

    static public Member create() {
        var parameter = new EasyRandomParameters();
        parameter.setStringLengthRange(new EasyRandomParameters.Range<>(1, 10));
        return new EasyRandom(parameter).nextObject(Member.class);
    }

    static public Member create(Long seed){
        var parameter = new EasyRandomParameters().seed(seed).stringLengthRange(1,10);
        return new EasyRandom(parameter).nextObject(Member.class);
    }

}
