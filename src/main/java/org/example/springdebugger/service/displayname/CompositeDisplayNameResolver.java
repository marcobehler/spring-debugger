package org.example.springdebugger.service.displayname;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * A composite implementation of DisplayNameResolver that tries multiple resolvers
 * in sequence and returns the first successful result.
 */
@Service
@Primary
@Profile("fallback")
public class CompositeDisplayNameResolver implements DisplayNameResolver {

    private final List<ResolverEntry> resolvers = new ArrayList<>();

    /**
     * Creates a new CompositeDisplayNameResolver with the specified resolvers.
     * The resolvers will be tried in the order they are provided.
     */
    @Autowired
    public CompositeDisplayNameResolver(
            SimpleDisplayNameResolver simpleResolver,
            LdapDisplayNameResolver ldapResolver,
            ActiveDirectoryDisplayNameResolver adResolver,
            OidDisplayNameResolver oidResolver,
            DatabaseDisplayNameResolver dbResolver) {

        // Add resolvers in order of preference
        addResolver(simpleResolver, "Simple");
        addResolver(ldapResolver, "LDAP");
        addResolver(adResolver, "Active Directory");
        addResolver(oidResolver, "Oracle Internet Directory");
        addResolver(dbResolver, "Database");
    }

    /**
     * Adds a resolver to the chain with a descriptive name.
     */
    public void addResolver(DisplayNameResolver resolver, String name) {
        resolvers.add(new ResolverEntry(resolver, name));
    }

    @Override
    public String resolveDisplayName(String username) {
        StringBuilder attemptLog = new StringBuilder();

        // Try each resolver in sequence
        for (ResolverEntry entry : resolvers) {
            try {
                String result = entry.getResolver().resolveDisplayName(username);

                // If the result is just the username, consider it a "not found"
                if (result.equals(username) || result.contains("Not found")) {
                    attemptLog.append(entry.getName()).append(" (no match), ");
                    continue;
                }

                // Found a result, return it with the source
                return result + " [Source: " + entry.getName() + "]";
            } catch (Exception e) {
                // Log the failure and continue to the next resolver
                attemptLog.append(entry.getName()).append(" (error: ")
                         .append(e.getMessage()).append("), ");
            }
        }

        // If we get here, all resolvers failed
        return username + " [No matches found. Attempted: " + 
               attemptLog.substring(0, attemptLog.length() - 2) + "]";
    }

    /**
     * Inner class representing a resolver entry with a descriptive name.
     */
    private static class ResolverEntry {
        private final DisplayNameResolver resolver;
        private final String name;

        public ResolverEntry(DisplayNameResolver resolver, String name) {
            this.resolver = resolver;
            this.name = name;
        }

        public DisplayNameResolver getResolver() {
            return resolver;
        }

        public String getName() {
            return name;
        }
    }
}
