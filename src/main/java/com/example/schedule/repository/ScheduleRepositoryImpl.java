package com.example.schedule.repository;

import com.example.schedule.dto.response.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.entity.User;
import com.example.schedule.exception.ScheduleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        LocalDateTime now = LocalDateTime.now();
        schedule.setCreate_time(now);
        schedule.setUpdate_time(now);

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule2").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("user_id", schedule.getUser().getId());
        parameters.put("post", schedule.getPost());
        parameters.put("password", schedule.getPassword());
        parameters.put("create_time", Timestamp.valueOf(schedule.getCreate_time()));
        parameters.put("update_time", Timestamp.valueOf(schedule.getUpdate_time()));

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        schedule.setId(key.longValue());

        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getPost(),
                schedule.getUser().getName(),
                schedule.getUser().getEmail(),
                schedule.getCreate_time(),
                schedule.getUpdate_time()
        );
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules(String update_time, String name, Long id, Integer page, Integer page_size) {
        // 필수 기능
//        if (update_time != null && name != null) {
//            return jdbcTemplate.query("select * from schedule2 join user on schedule2.user_id = user.id where name = ? and update_time = ? order by update_time desc", scheduleRowMapper(), name, update_time);
//        }
//        else if (update_time != null && name == null) {
//            return jdbcTemplate.query("select * from schedule2 join user on schedule2.user_id = user.id where update_time = ? order by update_time desc", scheduleRowMapper(), update_time);
//        }
//        else if (update_time == null && name != null) {
//            return jdbcTemplate.query("select * from schedule2 join user on schedule2.user_id = user.id where name = ? order by update_time desc", scheduleRowMapper(), name);
//        }
//        return jdbcTemplate.query("select * from schedule2 join user on schedule2.user_id = user.id order by update_time desc", scheduleRowMapper());
        // 도전 기능
        StringBuilder sql = new StringBuilder("select * from schedule2 join user on schedule2.user_id = user.id WHERE 1=1 ");
        List<Object> parameters = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            sql.append("and user.name = ? ");
            parameters.add(name);
        }

        if (update_time != null && !update_time.isEmpty()) {
            sql.append("and DATE(schedule2.update_time) = ? ");
            parameters.add(update_time);
        }

        if (id != null) {
            sql.append("and user.id = ? ");
            parameters.add(id);
        }

        sql.append("order by schedule2.update_time desc ");

        sql.append("limit ? offset ? ");
        parameters.add(page_size);
        parameters.add(page_size * (page-1));

        return jdbcTemplate.query(sql.toString(), scheduleRowMapper(), parameters.toArray());
    }

    @Override
    public Schedule findScheduleById(Long id) throws ScheduleNotFoundException {
        List<Schedule> result =  jdbcTemplate.query("select * from schedule2 join user on schedule2.user_id = user.id where schedule2.id = ?", scheduleRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(ScheduleNotFoundException::new);
    }

    @Override
    public Schedule updateSchedule(Schedule schedule) throws ScheduleNotFoundException {
        LocalDateTime now = LocalDateTime.now();
        schedule.setUpdate_time(now);

        String sql = "update schedule2 set post = ?, update_time = ? where id = ?";
        jdbcTemplate.update(sql, schedule.getPost(), schedule.getUpdate_time(), schedule.getId());
        return findScheduleById(schedule.getId());
    }

    @Override
    public void deleteScheduleById(Long id) {
        jdbcTemplate.update("delete from schedule2 where id = ?", id);
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("post"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getTimestamp("create_time").toLocalDateTime(),
                        rs.getTimestamp("update_time").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(
                        rs.getLong("user.id"),
                        rs.getString("name"),
                        rs.getString("email")
                );
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("post"),
                        rs.getString("password"),
                        user,
                        rs.getTimestamp("create_time").toLocalDateTime(),
                        rs.getTimestamp("update_time").toLocalDateTime()
                );
            }

        };
    }
}
