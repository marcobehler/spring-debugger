package org.example.springdebugger.service.ip;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
@Profile("profile10")
public class HostnameResolvingIpAddressService implements IpAddressService {

    @Override
    public String resolveClientIpAddress(HttpServletRequest request) {
        // This implementation attempts to resolve hostnames for IP addresses
        
        // First get the IP using standard approach
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
        
        // Try to resolve the hostname
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            String hostname = inetAddress.getHostName();
            
            // If we got a hostname (not just the IP again), return IP (hostname)
            if (!hostname.equals(ipAddress)) {
                return ipAddress + " (" + hostname + ")";
            }
        } catch (UnknownHostException e) {
            // Ignore resolution failures
        }
        
        return ipAddress;
    }
}