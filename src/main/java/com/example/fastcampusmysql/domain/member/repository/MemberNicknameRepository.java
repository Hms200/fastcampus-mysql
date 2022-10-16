package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberNicknameRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    static final String TABLLE = "MemberNicknameHistory";

    static final RowMapper<MemberNicknameHistory> rowMapper = (rs, rowNum) ->
         MemberNicknameHistory.builder()
                .id(rs.getLong("id"))
                .memberId(rs.getLong("memberId"))
                .nickname(rs.getString("nickname"))
                .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
                .build();

    public MemberNicknameHistory save(MemberNicknameHistory member) {
        /*
         *   goal - member id 존재여부를 통해 갱신 또는 삽입을 결정
         *   parameter - member
         *   return - id를 포함한 member
         * */
        if (member.getId() == null) {
            return insert(member);
        }
        throw new UnsupportedOperationException("update는 지원하지 않습니다.");
    }

    public MemberNicknameHistory insert (MemberNicknameHistory member){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName(TABLLE)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        var id = simpleJdbcInsert.executeAndReturnKey(param).longValue();

        return MemberNicknameHistory.builder()
                .id(id)
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public List<MemberNicknameHistory> findAllByMemberId(Long memberId) {
        String sql = String.format("SELECT * FROM %s WHERE memberId = :memberId", TABLLE);
        var param = new MapSqlParameterSource().addValue("memberId", memberId);
        return jdbcTemplate.query(sql, param, rowMapper);
    }

}
