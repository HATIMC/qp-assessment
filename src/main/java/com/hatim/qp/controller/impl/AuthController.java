package com.hatim.qp.controller.impl;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hatim.qp.controller.AuthenticationController;
import com.hatim.qp.controller.advice.exception.GroceryException;
import com.hatim.qp.security.dto.ApiKeyAuthRequest;
import com.hatim.qp.security.dto.ApiKeyAuthResponse;
import com.hatim.qp.security.service.IApiKeyAuthService;

@RestController
public class AuthController extends AuthenticationController {
    @Autowired
    IApiKeyAuthService apiKeyAuth;

    @PostMapping("/authenticate")
    public ResponseEntity<ApiKeyAuthResponse> authenticate(@RequestBody(required = false) ApiKeyAuthRequest request)
	    throws GroceryException {
	if (Objects.isNull(request)) {
	    throw new GroceryException(9, "Request Should Contain username and password");
	}
	String username = request.getUsername();
	String password = request.getPassword();
	if ((Objects.isNull(username) || Objects.isNull(password)) || (StringUtils.isBlank(username))
		|| (StringUtils.isBlank(password))) {
	    throw new GroceryException(9, "Request Should Contain username and password");
	}
	// Authenticate user and generate API key
	String apiKey = apiKeyAuth.isAuthenticated(username, password, null);

	if (apiKey != null) {
	    return ResponseEntity.ok(ApiKeyAuthResponse.builder().apiKey(apiKey).build());
	} else {
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
		    .body(ApiKeyAuthResponse.builder().apiKey(apiKey).build());
	}
    }
}
