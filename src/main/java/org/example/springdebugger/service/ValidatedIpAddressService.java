package org.example.springdebugger.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Profile("profile7")
public class ValidatedIpAddressService implements IpAddressService {

    // Pattern to validate IPv4 addresses
    private static final Pattern IPV4_PATTERN = 
        Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$");

    @Override
    public String resolveClientIpAddress(HttpServletRequest request) {
        // Try X-Forwarded-For first
        String ipAddress = request.getHeader("X-Forwarded-For");
        
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            // Fall back to remote address
            ipAddress = request.getRemoteAddr();
        } else {
            // Extract first IP from X-Forwarded-For
            int commaIndex = ipAddress.indexOf(',');
            if (commaIndex > 0) {
                ipAddress = ipAddress.substring(0, commaIndex).trim();
            }
        }
        
        // Validate the IP address format
        if (isValidIpv4Address(ipAddress)) {
            return ipAddress;
        } else {
            // Return a default IP if the resolved one is invalid
            return "127.0.0.1";
        }
    }
    
    private boolean isValidIpv4Address(String ipAddress) {
        if (ipAddress == null || ipAddress.isEmpty()) {
            return false;
        }
        
        Matcher matcher = IPV4_PATTERN.matcher(ipAddress);
        return matcher.matches();
    }
}