package org.example.springdebugger.service.ip;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class FastlyCloudflareIpAddressService implements IpAddressService {

    @Override
    public String resolveClientIpAddress(HttpServletRequest request) {
        // Check Fastly-Client-IP header first (used by Fastly CDN)
        String ipAddress = request.getHeader("Fastly-Client-IP");
        
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            // Fall back to CF-Connecting-IP (Cloudflare)
            ipAddress = request.getHeader("CF-Connecting-IP");
            
            if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
                // Finally fall back to remote address
                ipAddress = request.getRemoteAddr();
            }
        }
        
        return ipAddress;
    }
}