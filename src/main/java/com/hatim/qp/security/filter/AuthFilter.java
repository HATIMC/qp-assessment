package com.hatim.qp.security.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hatim.qp.db.repository.UserRepository;
import com.hatim.qp.security.service.IApiKeyAuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    AdminChecker adminChecker;
    @Autowired
    AuthChecker authChecker;
    @Autowired
    ApiChecker apiChecker;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	    throws ServletException, IOException {
	boolean authorized = true;

	RequestChecker checker = getRequestCheckerInstance(request.getRequestURI());
	if (checker == null || !checker.isAuthorized(request)) {
	    authorized = false;
	}

	if (authorized) {
	    filterChain.doFilter(request, response);
	} else {
	    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    response.setContentType("application/json");
	    try (ServletOutputStream outputStream = response.getOutputStream()) {
		if (request.getHeader("api_key") == null && !(checker instanceof AuthChecker)) {
		    outputStream.print("{\"error\": \"api_key header missing\"}");
		} else {
		    outputStream.print("{\"error\": \"Unauthorized\"}");
		}
	    }
	}
    }

    private RequestChecker getRequestCheckerInstance(String URI) {
	if (URI.contains("/admin/")) {
	    return adminChecker;
	} else if (URI.contains("/public/") || URI.contains("/auth/")) {
	    return authChecker;
	} else if (URI.contains("/api/")) {
	    return apiChecker;
	} else {
	    return null;
	}
    }
}

@Component
class AdminChecker implements RequestChecker {
    @Autowired
    IApiKeyAuthService apiKeyAuthService;
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isAuthorized(HttpServletRequest request) {

	int userId = apiKeyAuthService.isAuthenticated(request.getHeader("api_key"));
	if (userId != 0) {
	    var user = userRepository.findById(userId);
	    if (user.isPresent()) {
		return user.get().getUserType().equals("admin");
	    }
	}

	return false;
    }
}

@Component
class AuthChecker implements RequestChecker {
    @Override
    public boolean isAuthorized(HttpServletRequest request) {
	return true;
    }
}

@Component
class ApiChecker implements RequestChecker {
    @Autowired
    IApiKeyAuthService apiKeyAuthService;
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isAuthorized(HttpServletRequest request) {

	int userId = apiKeyAuthService.isAuthenticated(request.getHeader("api_key"));
	if (userId != 0) {
	    var user = userRepository.findById(userId);
	    if (user.isPresent()) {
		return user.get().getUserType().equals("user") || user.get().getUserType().equals("admin");
	    }
	}
	return false;
    }
}

interface RequestChecker {
    boolean isAuthorized(HttpServletRequest request);
}
