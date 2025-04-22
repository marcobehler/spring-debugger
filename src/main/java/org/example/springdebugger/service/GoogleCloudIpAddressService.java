package org.example.springdebugger.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("profile9")
public class GoogleCloudIpAddressService implements IpAddressService {

    @Override
    public String resolveClientIpAddress(HttpServletRequest request) {
        // This implementation focuses on headers used by Google Cloud services
        
        // Check for Google Cloud Load Balancer header
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress != null && !ipAddress.isEmpty() && !"unknown".equalsIgnoreCase(ipAddress)) {
            // Google Cloud Load Balancer adds multiple IPs to X-Forwarded-For
            // The rightmost IP is the most recent proxy, and the leftmost is the original client
            String[] ips = ipAddress.split(",");
            if (ips.length > 0) {
                return ips[0].trim(); // Return the leftmost IP (original client)
            }
        }
        
        // Check for App Engine specific header
        ipAddress = request.getHeader("X-Appengine-User-IP");
        if (ipAddress != null && !ipAddress.isEmpty()) {
            return ipAddress;
        }
        
        // Check for other Google-specific headers
        ipAddress = request.getHeader("X-Cloud-Trace-Context");
        if (ipAddress != null && !ipAddress.isEmpty()) {
            // In a real implementation, you might parse this for tracing
            // Here we just indicate it's from Google Cloud
            return "GCP-" + request.getRemoteAddr();
        }
        
        // Finally fall back to remote address
        return request.getRemoteAddr();
    }
}