package com.example.fastcampusmysql.domain.follow.repository;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class FollowRepository {

    static final String TABLE = "follow";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Follow> ROW_MAPPER = (rs, rowNum) -> Follow.builder()
            .id(rs.getLong("id"))
            .fromMemberId(rs.getLong("fromMemberId"))
            .toMemberId(rs.getLong("toMemberId"))
            .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
            .build();


    public Follow save(Follow follow) {
        if (follow.getId() == null) {
            return insert(follow);
        }
        throw new UnsupportedOperationException("update는 지원하지 않습니다.");
    }

    private Follow insert(Follow follow) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource param = new BeanPropertySqlParameterSource(follow);
        var id = jdbcInsert.executeAndReturnKey(param).longValue();

        return Follow.builder()
                .id(id)
                .fromMemberId(follow.getFromMemberId())
                .toMemberId(follow.getToMemberId())
                .createdAt(follow.getCreatedAt())
                .build();
    }

    public List<Follow> findAllByFromMemberId(Long fromMemberId) {
        var sql = String.format("SELECT * FROM %s WHERE fromMemberId = :fromMemberId", TABLE);
        var param = new MapSqlParameterSource().addValue("fromMemberId", fromMemberId);

        return jdbcTemplate.query(sql, param, ROW_MAPPER);
    }

}
