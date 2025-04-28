package org.example.springdebugger.service.displayname;

import jakarta.servlet.http.HttpServletRequest;

public interface DisplayNameResolver {

    String resolveDisplayName(String username);
}
