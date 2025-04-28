package org.example.springdebugger.service.displayname;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * A simulated database-backed implementation of DisplayNameResolver
 * that retrieves display names from a mock database.
 */
@Service
@Profile("database")
public class DatabaseDisplayNameResolver implements DisplayNameResolver {

    // Simulated database records
    private final Map<String, UserRecord> userDatabase = new HashMap<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DatabaseDisplayNameResolver() {
        // Initialize with some sample database records
        LocalDateTime now = LocalDateTime.now();

        userDatabase.put("marbehl", new UserRecord(1L, "Marco", "Behler", "marco@example.com", now.minusDays(30)));
        userDatabase.put("johdoe", new UserRecord(2L, "John", "Doe", "john@example.com", now.minusDays(45)));
        userDatabase.put("jansmi", new UserRecord(3L, "Jane", "Smith", "jane@example.com", now.minusDays(15)));
        userDatabase.put("bobjoh", new UserRecord(4L, "Bob", "Johnson", "bob@example.com", now.minusDays(60)));
        userDatabase.put("aliwil", new UserRecord(5L, "Alice", "Williams", "alice@example.com", now.minusDays(7)));
        userDatabase.put("davbro", new UserRecord(6L, "David", "Brown", "david@example.com", now.minusDays(90)));
        userDatabase.put("emmjon", new UserRecord(7L, "Emma", "Jones", "emma@example.com", now.minusDays(3)));
    }

    @Override
    public String resolveDisplayName(String username) {
        UserRecord record = userDatabase.get(username);
        if (record != null) {
            return String.format("%s %s (ID: %d, Email: %s, Last Login: %s)",
                    record.getFirstName(),
                    record.getLastName(),
                    record.getId(),
                    record.getEmail(),
                    record.getLastLogin().format(formatter));
        }
        return username + " [Not found in database]";
    }

    // Inner class to represent a database record
    private static class UserRecord {
        private final Long id;
        private final String firstName;
        private final String lastName;
        private final String email;
        private final LocalDateTime lastLogin;

        public UserRecord(Long id, String firstName, String lastName, String email, LocalDateTime lastLogin) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.lastLogin = lastLogin;
        }

        public Long getId() {
            return id;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public LocalDateTime getLastLogin() {
            return lastLogin;
        }
    }
}
