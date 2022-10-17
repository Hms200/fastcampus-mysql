package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class MemberRepository {
    static private final String TABLE = "member";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    static final RowMapper<Member> rowMapper = (ResultSet resultSet, int rowNum) ->
            Member.builder()
                    .id(resultSet.getLong("id"))
                    .email(resultSet.getString("email"))
                    .nickname(resultSet.getString("nickname"))
                    .birthday(resultSet.getDate("birthday").toLocalDate())
                    .createdAt(resultSet.getTimestamp("createdAt").toLocalDateTime())
                    .build();

    public Optional<Member> findById(Long id) {
        /*
        * select * from member where id = :id
        * */
        String sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE);
        var param = new MapSqlParameterSource().addValue("id", id);

            var member = jdbcTemplate.queryForObject(sql, param, rowMapper);
            return Optional.ofNullable(member);
    }

    public List<Member> findAllByIdIn(List<Long> ids) {
        // 빈 리스트일 때 비어있는 리스트를 그대로 리턴. sql grammar error 방지
        if (ids.isEmpty()) {
            return List.of();
        }
        var sql = String.format("SELECT * FROM %s WHERE id IN (:ids)", TABLE);
        var param = new MapSqlParameterSource().addValue("ids", ids);
        return jdbcTemplate.query(sql, param, rowMapper);
    }

    public Member save(Member member) {
        /*
        *   goal - member id 존재여부를 통해 갱신 또는 삽입을 결정
        *   parameter - member
        *   return - id를 포함한 member
        * */
        if (member.getId() == null) {
            return insert(member);
        }
        return update(member);
    }

    private Member insert(Member member) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName("Member")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        long id = simpleJdbcInsert.executeAndReturnKey(param).longValue();

        return Member.builder()
                .id(id)
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .createdAt(member.getCreatedAt())
                .build();
    }

    private Member update(Member member) {
        /*
        *   TODO:
        *   goal - member를 갱신
        *   parameter - member
        *   return - id를 포함한 member
        * */
        var sql = String.format("UPDATE %s SET email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id", TABLE );
        SqlParameterSource param = new BeanPropertySqlParameterSource(member);
        jdbcTemplate.update(sql, param);
        return member;
    }

}
