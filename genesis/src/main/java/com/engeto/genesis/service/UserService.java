package com.engeto.genesis.service;

import com.engeto.genesis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
        try {
            String personID = getNextIDFromFile(PERSON_ID_FILE);
            user.setPersonID(personID);
            String uuid = UUID.randomUUID().toString();
            user.setUuid(uuid);
            String sql = "insert into users values (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, user.getId(), user.getName(), user.getSurname(),
                    user.getPersonID(), user.getUuid());
        } catch (IOException e) {
            System.err.println("Error reading personID file: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println("No available personIDs: " + e.getMessage());
        }
        return user;
    }

    public User getUserByIDWithDetail(int id) {
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
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private String getNextIDFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmedID = line.trim();
                String checkSql = "select count(*) from users where person_id = ?";
                Integer count = jdbcTemplate.queryForObject(checkSql,
                        Integer.class, trimmedID);
                if (count != null && count == 0) {
                    return trimmedID;
                }
            }
        }
        throw new IllegalStateException("No available personIDs in the file.");
    }
}