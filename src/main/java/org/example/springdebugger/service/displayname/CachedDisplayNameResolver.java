package org.example.springdebugger.service.displayname;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A caching implementation of DisplayNameResolver that wraps another resolver
 * and caches the results to improve performance.
 */
@Service
@Profile("performance")
public class CachedDisplayNameResolver implements DisplayNameResolver {

    private final DisplayNameResolver delegate;
    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();
    private final Duration cacheTtl;

    /**
     * Creates a new CachedDisplayNameResolver with a default TTL of 1 hour.
     * 
     * @param delegate The resolver to delegate to for cache misses
     */
    @Autowired
    public CachedDisplayNameResolver(@Qualifier("simpleDisplayNameResolver") DisplayNameResolver delegate) {
        this(delegate, Duration.ofHours(1));
    }

    /**
     * Creates a new CachedDisplayNameResolver with the specified TTL.
     * 
     * @param delegate The resolver to delegate to for cache misses
     * @param cacheTtl The time-to-live for cache entries
     */
    public CachedDisplayNameResolver(DisplayNameResolver delegate, Duration cacheTtl) {
        this.delegate = delegate;
        this.cacheTtl = cacheTtl;
    }

    @Override
    public String resolveDisplayName(String username) {
        // Check if we have a valid cache entry
        CacheEntry entry = cache.get(username);

        if (entry != null && !entry.isExpired()) {
            return entry.getDisplayName() + " [Cached]";
        }

        // Cache miss or expired entry, delegate to the wrapped resolver
        String displayName = delegate.resolveDisplayName(username);

        // Store in cache
        cache.put(username, new CacheEntry(displayName, LocalDateTime.now().plus(cacheTtl)));

        return displayName + " [Fresh]";
    }

    /**
     * Clears all entries from the cache.
     */
    public void clearCache() {
        cache.clear();
    }

    /**
     * Returns the current size of the cache.
     */
    public int getCacheSize() {
        return cache.size();
    }

    /**
     * Inner class representing a cache entry with expiration.
     */
    private static class CacheEntry {
        private final String displayName;
        private final LocalDateTime expiresAt;

        public CacheEntry(String displayName, LocalDateTime expiresAt) {
            this.displayName = displayName;
            this.expiresAt = expiresAt;
        }

        public String getDisplayName() {
            return displayName;
        }

        public boolean isExpired() {
            return LocalDateTime.now().isAfter(expiresAt);
        }
    }
}
