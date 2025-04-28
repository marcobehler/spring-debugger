package org.example.springdebugger.service.displayname;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * A Gravatar-style implementation of DisplayNameResolver that generates
 * avatar URLs based on usernames and returns display names with avatar links.
 */
@Service
@Profile("avatar")
public class GravatarDisplayNameResolver implements DisplayNameResolver {

    // Simulated email mappings for users
    private final Map<String, String> userEmails = new HashMap<>();

    public GravatarDisplayNameResolver() {
        // Initialize with some sample email mappings
        userEmails.put("marbehl", "marco.behler@example.com");
        userEmails.put("johdoe", "john.doe@example.com");
        userEmails.put("jansmi", "jane.smith@example.com");
        userEmails.put("bobjoh", "bob.johnson@example.com");
        userEmails.put("aliwil", "alice.williams@example.com");
        userEmails.put("davbro", "david.brown@example.com");
        userEmails.put("emmjon", "emma.jones@example.com");
    }

    @Override
    public String resolveDisplayName(String username) {
        String email = userEmails.getOrDefault(username, username + "@example.com");
        String gravatarUrl = generateGravatarUrl(email);

        // Convert username to display name format
        String displayName = username.replace(".", " ");
        displayName = capitalizeWords(displayName);

        return displayName + " [Avatar: " + gravatarUrl + "]";
    }

    /**
     * Generates a Gravatar URL for the given email address.
     * This is a simplified version of how Gravatar works.
     */
    private String generateGravatarUrl(String email) {
        String hash = md5Hex(email.toLowerCase().trim());
        return "https://www.gravatar.com/avatar/" + hash + "?d=identicon&s=80";
    }

    /**
     * Generates an MD5 hash of the input string.
     */
    private String md5Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Fallback for environments where MD5 is not available
            return Integer.toHexString(input.hashCode());
        }
    }

    /**
     * Capitalizes the first letter of each word in a string.
     */
    private String capitalizeWords(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        StringBuilder result = new StringBuilder();
        String[] words = str.split("\\s");

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1).toLowerCase())
                      .append(" ");
            }
        }

        return result.toString().trim();
    }
}
