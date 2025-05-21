package com.engeto.genesis.service;

import com.engeto.genesis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String PERSON_ID_FILE = "genesis/src/main/resources/dataPersonId.txt";

    public User createUser(User user) {

        String personID = user.getPersonID();

        try {
            if (!personIDExistsInFile(personID)) {
                throw new IllegalArgumentException();
            }
            if (isPersonIDUsed(personID)) {
                throw new IllegalArgumentException();
            }
            String uuid = UUID.randomUUID().toString();
            user.setUuid(uuid);
            String sql = "insert into users (id, name, surname, person_id, uuid) values (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, user.getId(), user.getName(), user.getSurname(),
                    user.getPersonID(), user.getUuid());
        } catch (IOException e) {
            System.err.println("Error reading personID file: " + e.getMessage());
        }
        return user;
    }

    public User getUserByID(int id) {
        String sql = "select * from users where id = " + id;
        User user = jdbcTemplate.queryForObject(sql,
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet result, int rowNumber) throws SQLException {
                        User user = new User();
                        user.setId(result.getInt("id"));
                        user.setName(result.getString("name"));
                        user.setSurname(result.getString("surname"));
                        user.setPersonID(result.getString("person_id"));
                        user.setUuid(result.getString("uuid"));

                        return user;
                    }
                });
        return user;
    }

    public List<User> getAllUsers() {
        String sql = "select * from users";
        List<User> out = jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet result, int rowNumber) throws SQLException {
                User user = new User();
                user.setId(result.getInt("id"));
                user.setName(result.getString("name"));
                user.setSurname(result.getString("surname"));
                user.setPersonID(result.getString("person_id"));
                user.setUuid(result.getString("uuid"));
                return user;
            }
        });
        return out;
    }

    public void updateUserNameAndSurname(int id, String name, String surname) {
        String sql = "update users set name = ?, surname = ? where id = ?";
        jdbcTemplate.update(sql, name, surname, id);
    }

    public void deleteUserById(int id) {
        String checkSql = "select count(*) from users where id = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, id);

        if (count == null || count == 0) {
            throw new EmptyResultDataAccessException(1);
        }
        String sql = "delete from users where id = ?";
        jdbcTemplate.update(sql, id);
    }

    private boolean personIDExistsInFile(String personID) throws IOException {
        return Files.lines(Paths.get(PERSON_ID_FILE))
                .anyMatch(line -> line.trim().equals(personID));
    }

    private boolean isPersonIDUsed(String personID) {
        String sql = "select count(*) from users where person_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class,
                personID);
        return count != null && count > 0;
    }

}