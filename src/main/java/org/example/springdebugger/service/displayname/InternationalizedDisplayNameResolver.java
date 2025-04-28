package org.example.springdebugger.service.displayname;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * An internationalized implementation of DisplayNameResolver that provides
 * display names in different languages based on locale.
 */
@Service
@Profile("i18n")
public class InternationalizedDisplayNameResolver implements DisplayNameResolver {

    // Map of usernames to their localized display names
    private final Map<String, Map<String, String>> localizedNames = new HashMap<>();

    public InternationalizedDisplayNameResolver() {
        // Initialize with some sample localized names

        // Marco Behler
        Map<String, String> marcoNames = new HashMap<>();
        marcoNames.put("en", "Marco Behler");
        marcoNames.put("de", "Marco Behler");
        marcoNames.put("fr", "Marco Behler");
        marcoNames.put("es", "Marco Behler");
        marcoNames.put("ja", "マルコ・ベーラー");
        marcoNames.put("zh", "马可·贝勒");
        localizedNames.put("marbehl", marcoNames);

        // John Doe
        Map<String, String> johnNames = new HashMap<>();
        johnNames.put("en", "John Doe");
        johnNames.put("de", "Johann Doe");
        johnNames.put("fr", "Jean Doe");
        johnNames.put("es", "Juan Doe");
        johnNames.put("ja", "ジョン・ドウ");
        johnNames.put("zh", "约翰·多伊");
        localizedNames.put("johdoe", johnNames);

        // Jane Smith
        Map<String, String> janeNames = new HashMap<>();
        janeNames.put("en", "Jane Smith");
        janeNames.put("de", "Jana Schmidt");
        janeNames.put("fr", "Jeanne Smith");
        janeNames.put("es", "Juana Smith");
        janeNames.put("ja", "ジェーン・スミス");
        janeNames.put("zh", "简·史密斯");
        localizedNames.put("jansmi", janeNames);

        // Add more users with localized names as needed
    }

    @Override
    public String resolveDisplayName(String username) {
        // Get the current locale or default to English
        Locale currentLocale = Locale.getDefault();
        String languageCode = currentLocale.getLanguage();

        // Get localized names for this user
        Map<String, String> names = localizedNames.get(username);

        if (names != null) {
            // Try to get name in current language
            String localizedName = names.get(languageCode);

            // If not found, fall back to English
            if (localizedName == null) {
                localizedName = names.get("en");
            }

            if (localizedName != null) {
                // Return the localized name with language info
                return localizedName + " [" + new Locale(languageCode).getDisplayLanguage(Locale.ENGLISH) + "]";
            }
        }

        // Fallback: just return the username
        return username;
    }

    /**
     * For testing: resolve a display name in a specific language
     */
    public String resolveDisplayNameInLanguage(String username, String languageCode) {
        Map<String, String> names = localizedNames.get(username);

        if (names != null) {
            String localizedName = names.get(languageCode);

            if (localizedName != null) {
                return localizedName + " [" + new Locale(languageCode).getDisplayLanguage(Locale.ENGLISH) + "]";
            }
        }

        return username;
    }
}
