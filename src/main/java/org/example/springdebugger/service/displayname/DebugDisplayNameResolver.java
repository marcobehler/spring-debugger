package org.example.springdebugger.service.displayname;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * A debug implementation of DisplayNameResolver that includes the IP address
 * along with the username for debugging purposes.
 */
@Service
@Profile("dev")
public class DebugDisplayNameResolver implements DisplayNameResolver {

    private final DisplayNameResolver delegate;

    @Autowired
    public DebugDisplayNameResolver(SimpleDisplayNameResolver delegate) {
        this.delegate = delegate;
    }

    @Override
    public String resolveDisplayName(String username) {
        String displayName = delegate.resolveDisplayName(username);
        
        // Try to get the current request
        ServletRequestAttributes attributes = 
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String ipAddress = request.getRemoteAddr();
            return displayName + " [IP: " + ipAddress + "]";
        }
        
        return displayName + " [IP: unknown]";
    }
}