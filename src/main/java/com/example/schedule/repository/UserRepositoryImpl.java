package com.example.schedule.repository;

import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.dto.response.UserResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserResponseDto saveUser(User user) {
        LocalDateTime now = LocalDateTime.now();
        user.setCreate_time(now);
        user.setUpdate_time(now);

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", user.getName());
        parameters.put("email", user.getEmail());
        parameters.put("create_time", Timestamp.valueOf(user.getCreate_time()));
        parameters.put("update_time", Timestamp.valueOf(user.getUpdate_time()));

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        user.setId(key.longValue());

        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreate_time(),
                user.getUpdate_time()
        );
    }

    @Override
    public User findUserByEmail(String email) {
        List<User> result = jdbcTemplate.query("select * from user where email = ?", userRowMapperV2(), email);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public User findUserById(Long id) {
        List<User> result = jdbcTemplate.query("select * from user where id = ?", userRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public User updateUser(Long id, User user) {
        LocalDateTime now = LocalDateTime.now();
        user.setUpdate_time(now);

        String sql = "update user set name = ?, email = ?, update_time = ? where id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getUpdate_time(), user.getId());
        return findUserById(user.getId());
    }

    @Override
    public void deleteUserById(Long id) {
        jdbcTemplate.update("delete from user where id = ?", id);
    }

    @Override
    public boolean existByEmail(String email) {
        String sql = "select count(*) from user where email = ?";
        if (jdbcTemplate.queryForObject(sql, Integer.class, email) > 0) {
            return true;
        }
        return false;
    }


//    private RowMapper<UserResponseDto> userRowMapper() {
//        return new RowMapper<UserResponseDto>() {
//            @Override
//            public UserResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
//                return new UserResponseDto(
//                        rs.getLong("id"),
//                        rs.getString("name"),
//                        rs.getString("email"),
//                        rs.getTimestamp("create_time").toLocalDateTime(),
//                        rs.getTimestamp("update_time").toLocalDateTime()
//                );
//            }
//        };
//    }

    private RowMapper<User> userRowMapperV2() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                user.setCreate_time(rs.getTimestamp("create_time").toLocalDateTime());
                user.setUpdate_time(rs.getTimestamp("update_time").toLocalDateTime());
                return user;
            }
        };
    }
}
