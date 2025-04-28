package org.example.springdebugger.service.displayname;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * A simulated Active Directory implementation of DisplayNameResolver
 * that retrieves display names from a mock Active Directory.
 */
@Service
@Profile("windows")
public class ActiveDirectoryDisplayNameResolver implements DisplayNameResolver {

    // Simulated Active Directory entries
    private final Map<String, AdUserEntry> adDirectory = new HashMap<>();

    public ActiveDirectoryDisplayNameResolver() {
        // Initialize with some sample Active Directory entries
        adDirectory.put("marbehl", new AdUserEntry("Marco Behler", "DOMAIN\\mbehler", "Engineering", true));
        adDirectory.put("johdoe", new AdUserEntry("John Doe", "DOMAIN\\jdoe", "Marketing", true));
        adDirectory.put("jansmi", new AdUserEntry("Jane Smith", "DOMAIN\\jsmith", "Human Resources", true));
        adDirectory.put("bobjoh", new AdUserEntry("Bob Johnson", "DOMAIN\\bjohnson", "Sales", true));
        adDirectory.put("aliwil", new AdUserEntry("Alice Williams", "DOMAIN\\awilliams", "Finance", true));
        adDirectory.put("davbro", new AdUserEntry("David Brown", "DOMAIN\\dbrown", "Information Technology", false));
        adDirectory.put("emmjon", new AdUserEntry("Emma Jones", "DOMAIN\\ejones", "Customer Support", true));
    }

    @Override
    public String resolveDisplayName(String username) {
        AdUserEntry entry = adDirectory.get(username);
        if (entry != null) {
            StringBuilder result = new StringBuilder(entry.getDisplayName());

            // Add domain account info
            result.append(" (").append(entry.getDomainAccount()).append(")");

            // Add department
            result.append(" - ").append(entry.getDepartment());

            // Add account status
            if (!entry.isEnabled()) {
                result.append(" [DISABLED]");
            }

            return result.toString();
        }
        return username + " [Not found in AD]";
    }

    // Inner class to represent an Active Directory entry
    private static class AdUserEntry {
        private final String displayName;
        private final String domainAccount;
        private final String department;
        private final boolean enabled;

        public AdUserEntry(String displayName, String domainAccount, String department, boolean enabled) {
            this.displayName = displayName;
            this.domainAccount = domainAccount;
            this.department = department;
            this.enabled = enabled;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getDomainAccount() {
            return domainAccount;
        }

        public String getDepartment() {
            return department;
        }

        public boolean isEnabled() {
            return enabled;
        }
    }
}
