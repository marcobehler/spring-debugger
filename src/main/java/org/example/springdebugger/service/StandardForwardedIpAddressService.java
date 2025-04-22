package org.example.springdebugger.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("profile6")
public class StandardForwardedIpAddressService implements IpAddressService {

    @Override
    public String resolveClientIpAddress(HttpServletRequest request) {
        // This implementation checks the Forwarded header (RFC 7239)
        // which is a standardized way to identify the originating IP address
        String forwarded = request.getHeader("Forwarded");
        if (forwarded != null) {
            // Parse the Forwarded header which can be complex
            // Example: Forwarded: for=192.0.2.60;proto=http;by=203.0.113.43
            for (String part : forwarded.split(";")) {
                if (part.trim().startsWith("for=")) {
                    String forValue = part.trim().substring(4);
                    // Remove possible port information and quotes
                    forValue = forValue.replaceAll("\"", "");
                    int colonIndex = forValue.indexOf(':');
                    if (colonIndex > 0) {
                        forValue = forValue.substring(0, colonIndex);
                    }
                    // Remove IPv6 brackets if present
                    if (forValue.startsWith("[") && forValue.endsWith("]")) {
                        forValue = forValue.substring(1, forValue.length() - 1);
                    }
                    return forValue;
                }
            }
        }
        
        // Fall back to remote address if Forwarded header is not present or invalid
        return request.getRemoteAddr();
    }
}