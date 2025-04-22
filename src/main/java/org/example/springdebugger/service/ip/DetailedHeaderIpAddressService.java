package org.example.springdebugger.service.ip;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Profile("profile11")
public class DetailedHeaderIpAddressService implements IpAddressService {

    @Override
    public String resolveClientIpAddress(HttpServletRequest request) {
        // This implementation provides detailed information about all headers
        // that might contain IP information
        
        Map<String, String> ipHeaders = new HashMap<>();
        
        // Check all common IP-related headers
        String[] headerNames = {
            "X-Forwarded-For", 
            "Proxy-Client-IP", 
            "WL-Proxy-Client-IP", 
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED", 
            "HTTP_X_CLUSTER_CLIENT_IP", 
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR", 
            "HTTP_FORWARDED", 
            "HTTP_VIA", 
            "REMOTE_ADDR",
            "X-Real-IP",
            "True-Client-IP",
            "Fastly-Client-IP",
            "CF-Connecting-IP",
            "Forwarded"
        };
        
        for (String headerName : headerNames) {
            String headerValue = request.getHeader(headerName);
            if (headerValue != null && !headerValue.isEmpty() && !"unknown".equalsIgnoreCase(headerValue)) {
                ipHeaders.put(headerName, headerValue);
            }
        }
        
        // If we found any headers, use the first one in our list that has a value
        for (String headerName : headerNames) {
            if (ipHeaders.containsKey(headerName)) {
                String ipAddress = ipHeaders.get(headerName);
                // Extract first IP if multiple are present
                int commaIndex = ipAddress.indexOf(',');
                if (commaIndex > 0) {
                    ipAddress = ipAddress.substring(0, commaIndex).trim();
                }
                return ipAddress + " (via " + headerName + ")";
            }
        }
        
        // If no headers were found, use remote address
        return request.getRemoteAddr() + " (direct)";
    }
}