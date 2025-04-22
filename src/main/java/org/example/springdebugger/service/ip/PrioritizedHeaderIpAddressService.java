package org.example.springdebugger.service.ip;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Profile("profile4")
public class PrioritizedHeaderIpAddressService implements IpAddressService {

    private static final List<String> HEADER_ORDER = Arrays.asList(
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
            "REMOTE_ADDR"
    );

    @Override
    public String resolveClientIpAddress(HttpServletRequest request) {
        // Try multiple headers in order of preference
        for (String header : HEADER_ORDER) {
            String ipAddress = request.getHeader(header);
            if (ipAddress != null && !ipAddress.isEmpty() && !"unknown".equalsIgnoreCase(ipAddress)) {
                // Extract first IP if multiple are present
                int commaIndex = ipAddress.indexOf(',');
                if (commaIndex > 0) {
                    ipAddress = ipAddress.substring(0, commaIndex).trim();
                }
                return ipAddress;
            }
        }
        
        // If all headers failed, use remote address
        return request.getRemoteAddr();
    }
}