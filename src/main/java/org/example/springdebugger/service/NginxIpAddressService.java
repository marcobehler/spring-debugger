package org.example.springdebugger.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("profile2")
public class NginxIpAddressService implements IpAddressService {

    @Override
    public String resolveClientIpAddress(HttpServletRequest request) {
        // Check X-Real-IP header first (used by Nginx)
        String ipAddress = request.getHeader("X-Real-IP");
        
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            // Fall back to X-Forwarded-For
            ipAddress = request.getHeader("X-Forwarded-For");
            
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                // Finally fall back to remote address
                ipAddress = request.getRemoteAddr();
            } else {
                // Extract first IP from X-Forwarded-For
                int commaIndex = ipAddress.indexOf(',');
                if (commaIndex > 0) {
                    ipAddress = ipAddress.substring(0, commaIndex).trim();
                }
            }
        }
        
        return ipAddress;
    }
}