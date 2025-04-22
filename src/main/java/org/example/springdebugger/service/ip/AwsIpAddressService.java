package org.example.springdebugger.service.ip;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("profile8")
public class AwsIpAddressService implements IpAddressService {

    @Override
    public String resolveClientIpAddress(HttpServletRequest request) {
        // This implementation prioritizes headers used by AWS services
        
        // Check for AWS ALB/ELB headers
        String ipAddress = request.getHeader("X-Amzn-Trace-Id");
        if (ipAddress != null && !ipAddress.isEmpty()) {
            // Extract client IP from AWS trace ID if possible
            // Format: Root=1-67891233-abcdef012345678912345678;Self=1-67891233-12456789
            // This is a simplified example - in reality, you'd need more complex parsing
            return "AWS-" + request.getRemoteAddr();
        }
        
        // Try AWS CloudFront header
        ipAddress = request.getHeader("CloudFront-Viewer-Country-Name");
        if (ipAddress != null && !ipAddress.isEmpty()) {
            // In a real implementation, you might use this for geolocation
            // Here we just prepend it to the IP
            return ipAddress + "-" + request.getRemoteAddr();
        }
        
        // Fall back to standard X-Forwarded-For
        ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress != null && !ipAddress.isEmpty() && !"unknown".equalsIgnoreCase(ipAddress)) {
            int commaIndex = ipAddress.indexOf(',');
            if (commaIndex > 0) {
                ipAddress = ipAddress.substring(0, commaIndex).trim();
            }
            return ipAddress;
        }
        
        // Finally fall back to remote address
        return request.getRemoteAddr();
    }
}