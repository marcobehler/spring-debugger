package org.example.springdebugger.service.displayname;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * A mock implementation of DisplayNameResolver that returns random names
 * for testing purposes.
 */
@Service
@Profile("dev")
@Primary
public class MockDisplayNameResolver implements DisplayNameResolver {

    private final Random random = new Random();
    
    private final List<String> mockNames = List.of(
            "Test User",
            "Sample Person",
            "Mock Individual",
            "Dummy Account",
            "QA Tester",
            "Automated User",
            "Integration Test",
            "System Test",
            "Unit Test",
            "Acceptance Test"
    );

    @Override
    public String resolveDisplayName(String username) {
        // Return a random name from the list
        return mockNames.get(random.nextInt(mockNames.size()));
    }
}