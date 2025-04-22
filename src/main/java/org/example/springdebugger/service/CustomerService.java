package org.example.springdebugger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class CustomerService {

    private final JdbcTemplate jdbcTemplate;
    private final Random random = new Random();

    @Autowired
    public CustomerService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createRandomCustomer() {
        String username = "user_" + UUID.randomUUID().toString().substring(0, 8);
        String email = username + "@example.com";
        String password = "password_" + UUID.randomUUID().toString().substring(0, 8);
        String firstName = getRandomFirstName();
        String lastName = getRandomLastName();
        boolean active = random.nextBoolean();

        jdbcTemplate.update(
            "INSERT INTO customers (username, email, password, first_name, last_name, active) VALUES (?, ?, ?, ?, ?, ?)",
            username, email, password, firstName, lastName, active
        );
    }

    private String getRandomFirstName() {
        String[] firstNames = {"John", "Jane", "Bob", "Alice", "Charlie", "Diana", "Edward", "Fiona", "George", "Hannah"};
        return firstNames[random.nextInt(firstNames.length)];
    }

    private String getRandomLastName() {
        String[] lastNames = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor"};
        return lastNames[random.nextInt(lastNames.length)];
    }
}