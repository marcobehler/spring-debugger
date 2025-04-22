package org.example.springdebugger.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DefaultIpAddressService implements IpAddressService {

    @Override
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