package org.example.springdebugger.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class IpAddressService {

    /**
     * Resolves the client's IP address from the HTTP request.
     * Checks for X-Forwarded-For header first (for clients behind proxies),
     * then falls back to the remote address.
     *
     * @param request The HTTP request
     * @return The client's IP address
     */
    public String resolveClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        } else {
            // X-Forwarded-For might contain multiple IPs (client, proxy1, proxy2, ...)
            // The first one is the original client IP
            int commaIndex = ipAddress.indexOf(',');
            if (commaIndex > 0) {
                ipAddress = ipAddress.substring(0, commaIndex).trim();
            }
        }
        
        return ipAddress;
    }
}