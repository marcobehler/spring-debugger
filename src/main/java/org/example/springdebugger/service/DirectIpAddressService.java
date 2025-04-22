package org.example.springdebugger.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DirectIpAddressService implements IpAddressService {

    @Override
    public String resolveClientIpAddress(HttpServletRequest request) {
        // This implementation always returns the remote address directly
        // without checking any headers - useful for environments where
        // you know the client connects directly without proxies
        return request.getRemoteAddr();
    }
}