package org.example.springdebugger.service.displayname;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * A random name generator implementation of DisplayNameResolver that generates
 * random display names for testing or demo purposes.
 */
@Service
@Profile("demo")
public class RandomNameDisplayNameResolver implements DisplayNameResolver {

    private final Random random = new Random();
    
    // Lists of first names and last names for random generation
    private final List<String> firstNames = Arrays.asList(
            "James", "Mary", "John", "Patricia", "Robert", "Jennifer", "Michael", "Linda",
            "William", "Elizabeth", "David", "Barbara", "Richard", "Susan", "Joseph", "Jessica",
            "Thomas", "Sarah", "Charles", "Karen", "Christopher", "Nancy", "Daniel", "Lisa",
            "Matthew", "Betty", "Anthony", "Margaret", "Mark", "Sandra", "Donald", "Ashley",
            "Steven", "Kimberly", "Paul", "Emily", "Andrew", "Donna", "Joshua", "Michelle",
            "Kenneth", "Dorothy", "Kevin", "Carol", "Brian", "Amanda", "George", "Melissa"
    );
    
    private final List<String> lastNames = Arrays.asList(
            "Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson",
            "Moore", "Taylor", "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin",
            "Thompson", "Garcia", "Martinez", "Robinson", "Clark", "Rodriguez", "Lewis", "Lee",
            "Walker", "Hall", "Allen", "Young", "Hernandez", "King", "Wright", "Lopez",
            "Hill", "Scott", "Green", "Adams", "Baker", "Gonzalez", "Nelson", "Carter",
            "Mitchell", "Perez", "Roberts", "Turner", "Phillips", "Campbell", "Parker", "Evans"
    );
    
    // Lists of titles and departments for more variety
    private final List<String> titles = Arrays.asList(
            "Developer", "Engineer", "Manager", "Director", "Analyst", "Specialist",
            "Consultant", "Coordinator", "Administrator", "Designer", "Architect"
    );
    
    private final List<String> departments = Arrays.asList(
            "Engineering", "Marketing", "Sales", "HR", "Finance", "IT", "Operations",
            "Customer Support", "Research", "Product", "Legal"
    );

    @Override
    public String resolveDisplayName(String username) {
        // Generate a random name
        String firstName = firstNames.get(random.nextInt(firstNames.size()));
        String lastName = lastNames.get(random.nextInt(lastNames.size()));
        
        // Optionally add a title and department
        if (random.nextBoolean()) {
            String title = titles.get(random.nextInt(titles.size()));
            String department = departments.get(random.nextInt(departments.size()));
            
            return String.format("%s %s (%s, %s) [Random: %s]", 
                    firstName, lastName, title, department, username);
        } else {
            return String.format("%s %s [Random: %s]", firstName, lastName, username);
        }
    }
    
    /**
     * Generate a consistent random name based on the username (deterministic).
     * This is useful for demos where you want the same random name for the same username.
     */
    public String generateConsistentRandomName(String username) {
        // Use the username as a seed for the random generator
        Random seededRandom = new Random(username.hashCode());
        
        String firstName = firstNames.get(seededRandom.nextInt(firstNames.size()));
        String lastName = lastNames.get(seededRandom.nextInt(lastNames.size()));
        
        return String.format("%s %s [Seeded Random: %s]", firstName, lastName, username);
    }
}