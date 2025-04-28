package org.example.springdebugger.service.displayname;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A simulated social media implementation of DisplayNameResolver
 * that retrieves display names from mock social media profiles.
 */
@Service
@Profile("social")
public class SocialMediaDisplayNameResolver implements DisplayNameResolver {

    // Simulated social media profiles
    private final Map<String, SocialProfile> socialProfiles = new HashMap<>();
    private final Random random = new Random();

    public SocialMediaDisplayNameResolver() {
        // Initialize with some sample social media profiles
        socialProfiles.put("marbehl", new SocialProfile("Marco Behler", "@marcobehler", 5432, 1234, "Java Developer"));
        socialProfiles.put("johdoe", new SocialProfile("John Doe", "@johndoe", 1200, 800, "Marketing Professional"));
        socialProfiles.put("jansmi", new SocialProfile("Jane Smith", "@janesmith", 3500, 2100, "HR Specialist"));
        socialProfiles.put("bobjoh", new SocialProfile("Bob Johnson", "@bobjohnson", 950, 600, "Sales Expert"));
        socialProfiles.put("aliwil", new SocialProfile("Alice Williams", "@alicew", 2800, 1500, "Financial Advisor"));
        socialProfiles.put("davbro", new SocialProfile("David Brown", "@davidbrown", 1800, 900, "IT Professional"));
        socialProfiles.put("emmjon", new SocialProfile("Emma Jones", "@emmaj", 4200, 3100, "Customer Support"));
    }

    @Override
    public String resolveDisplayName(String username) {
        SocialProfile profile = socialProfiles.get(username);
        if (profile != null) {
            // Simulate some random online status
            boolean isOnline = random.nextBoolean();
            String status = isOnline ? "Online" : "Offline";

            return String.format("%s (%s) - %s | %d followers, %d following | %s",
                    profile.getDisplayName(),
                    profile.getHandle(),
                    profile.getBio(),
                    profile.getFollowers(),
                    profile.getFollowing(),
                    status);
        }
        return username + " [No social profile found]";
    }

    // Inner class to represent a social media profile
    private static class SocialProfile {
        private final String displayName;
        private final String handle;
        private final int followers;
        private final int following;
        private final String bio;

        public SocialProfile(String displayName, String handle, int followers, int following, String bio) {
            this.displayName = displayName;
            this.handle = handle;
            this.followers = followers;
            this.following = following;
            this.bio = bio;
        }

        public String getDisplayName() {
            return displayName;
        }

        public String getHandle() {
            return handle;
        }

        public int getFollowers() {
            return followers;
        }

        public int getFollowing() {
            return following;
        }

        public String getBio() {
            return bio;
        }
    }
}
