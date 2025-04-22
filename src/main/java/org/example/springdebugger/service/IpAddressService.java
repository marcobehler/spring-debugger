package org.example.springdebugger.service;

import jakarta.servlet.http.HttpServletRequest;

public interface IpAddressService {
    /**
     * Resolves the client's IP address from the HTTP request.
     * Checks for X-Forwarded-For header first (for clients behind proxies),
     * then falls back to the remote address.
     *
     * @param request The HTTP request
     * @return The client's IP address
     */
    String resolveClientIpAddress(HttpServletRequest request);
}