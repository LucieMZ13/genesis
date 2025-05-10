package com.engeto.genesis.service;

import com.engeto.genesis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String PERSON_ID_FILE = "resources/dataPersonId.txt";

    public User createUser(User user) {
        try {
        String personID = getNextIDFromFile(PERSON_ID_FILE);
        user.setPersonID(personID);
        String uuid = UUID.randomUUID().toString();
        user.setUuid(uuid);
        String sql = "insert into users values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,user.getId(),user.getName(),user.getSurname(),
                user.getPersonID(), user.getUuid());
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
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
                        user.setId(result.getInt(id));
                        user.setName(result.getString("name"));
                        user.setSurname(result.getString("surname"));
                        user.setPersonID(result.getString("person_ID"));
                        user.setUuid(result.getString("uuid"));

                        return user;
                    }
                });
        return user;
    }
    
    private String getNextIDFromFile(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(
                new FileReader(filename)));
        int lineNumber = 0;
        String nextIDFromFile = "";
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            lineNumber++;
            nextIDFromFile = line;
        }
        return nextIDFromFile;
    }
}