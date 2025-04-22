package org.example.springdebugger.service.ip;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Profile("profile12")
@Primary
public class TimestampedIpAddressService implements IpAddressService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String resolveClientIpAddress(HttpServletRequest request) {
        // This implementation adds a timestamp to the IP address
        
        // Get IP using standard approach
        String ipAddress = request.getHeader("X-Forwarded-For");
        
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        } else {
            // Extract first IP from X-Forwarded-For
            int commaIndex = ipAddress.indexOf(',');
            if (commaIndex > 0) {
                ipAddress = ipAddress.substring(0, commaIndex).trim();
            }
        }
        
        // Add current timestamp
        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(FORMATTER);
        
        return ipAddress + " (accessed at " + timestamp + ")";
    }
}