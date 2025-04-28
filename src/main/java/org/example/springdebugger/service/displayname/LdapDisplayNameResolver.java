package org.example.springdebugger.service.displayname;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * A simulated LDAP implementation of DisplayNameResolver that retrieves
 * display names from a mock LDAP directory.
 */
@Service
@Profile("directory")
public class LdapDisplayNameResolver implements DisplayNameResolver {

    // Simulated LDAP directory entries
    private final Map<String, LdapEntry> ldapDirectory = new HashMap<>();

    public LdapDisplayNameResolver() {
        // Initialize with some sample LDAP entries
        ldapDirectory.put("marbehl", new LdapEntry("Marco", "Behler", "Engineering", "Senior Developer"));
        ldapDirectory.put("johdoe", new LdapEntry("John", "Doe", "Marketing", "Marketing Manager"));
        ldapDirectory.put("jansmi", new LdapEntry("Jane", "Smith", "HR", "HR Director"));
        ldapDirectory.put("bobjoh", new LdapEntry("Bob", "Johnson", "Sales", "Sales Representative"));
        ldapDirectory.put("aliwil", new LdapEntry("Alice", "Williams", "Finance", "Financial Analyst"));
        ldapDirectory.put("davbro", new LdapEntry("David", "Brown", "IT", "System Administrator"));
        ldapDirectory.put("emmjon", new LdapEntry("Emma", "Jones", "Customer Support", "Support Specialist"));
    }

    @Override
    public String resolveDisplayName(String username) {
        LdapEntry entry = ldapDirectory.get(username);
        if (entry != null) {
            return entry.getFirstName() + " " + entry.getLastName() + 
                   " (" + entry.getDepartment() + ", " + entry.getTitle() + ")";
        }
        return username;
    }

    // Inner class to represent an LDAP entry
    private static class LdapEntry {
        private final String firstName;
        private final String lastName;
        private final String department;
        private final String title;

        public LdapEntry(String firstName, String lastName, String department, String title) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.department = department;
            this.title = title;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getDepartment() {
            return department;
        }

        public String getTitle() {
            return title;
        }
    }
}
