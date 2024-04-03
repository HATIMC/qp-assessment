package com.hatim.qp.security.service;

public interface IApiKeyAuthService {
    String isAuthenticated(String userName, String password, String userApiKey);
    int isAuthenticated(String apiKey);
}
