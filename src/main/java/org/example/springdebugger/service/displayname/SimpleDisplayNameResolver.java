package org.example.springdebugger.service.displayname;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * A simple implementation of DisplayNameResolver that maps usernames to real names
 * using a predefined mapping.
 */
@Service
@Profile("dev")
public class SimpleDisplayNameResolver implements DisplayNameResolver {

    private final Map<String, String> userToRealNameMap = Map.of(
            "marbehl", "Marco Behler",
            "johdoe", "John Doe",
            "jansmi", "Jane Smith",
            "bobjoh", "Bob Johnson",
            "aliwil", "Alice Williams",
            "davbro", "David Brown",
            "emmjon", "Emma Jones",
            "micmil", "Michael Miller",
            "olidav", "Olivia Davis",
            "wilwil", "William Wilson"
    );

    @Override
    public String resolveDisplayName(String username) {
        return userToRealNameMap.getOrDefault(username, username);
    }
}
