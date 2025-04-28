package org.example.springdebugger.service.displayname;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * A simulated Oracle Internet Directory (OID) implementation of DisplayNameResolver
 * that retrieves display names from a mock OID.
 */
@Service
@Profile("oracle")
public class OidDisplayNameResolver implements DisplayNameResolver {

    // Simulated OID entries
    private final Map<String, OidUserEntry> oidDirectory = new HashMap<>();

    public OidDisplayNameResolver() {
        // Initialize with some sample OID entries
        oidDirectory.put("marbehl", new OidUserEntry("Marco Behler", "cn=mbehler,ou=developers,o=company", "ACTIVE"));
        oidDirectory.put("johdoe", new OidUserEntry("John Doe", "cn=jdoe,ou=marketing,o=company", "ACTIVE"));
        oidDirectory.put("jansmi", new OidUserEntry("Jane Smith", "cn=jsmith,ou=hr,o=company", "ACTIVE"));
        oidDirectory.put("bobjoh", new OidUserEntry("Bob Johnson", "cn=bjohnson,ou=sales,o=company", "ACTIVE"));
        oidDirectory.put("aliwil", new OidUserEntry("Alice Williams", "cn=awilliams,ou=finance,o=company", "ACTIVE"));
        oidDirectory.put("davbro", new OidUserEntry("David Brown", "cn=dbrown,ou=it,o=company", "INACTIVE"));
        oidDirectory.put("emmjon", new OidUserEntry("Emma Jones", "cn=ejones,ou=support,o=company", "ACTIVE"));
    }

    @Override
    public String resolveDisplayName(String username) {
        OidUserEntry entry = oidDirectory.get(username);
        if (entry != null) {
            if ("ACTIVE".equals(entry.getStatus())) {
                return entry.getDisplayName() + " [OID: " + entry.getDn() + "]";
            } else {
                return entry.getDisplayName() + " [INACTIVE]";
            }
        }
        return username + " [Not found in OID]";
    }

    // Inner class to represent an OID entry
    private static class OidUserEntry {
        private final String displayName;
        private final String dn; // Distinguished Name
        private final String status;

        public OidUserEntry(String displayName, String dn, String status) {
            this.displayName = displayName;
            this.dn = dn;
            this.status = status;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getDn() {
            return dn;
        }

        public String getStatus() {
            return status;
        }
    }
}
