package com.hatim.qp.security.service.impl;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hatim.qp.db.model.ApiKey;
import com.hatim.qp.db.repository.ApiKeyRepository;
import com.hatim.qp.db.repository.UserRepository;
import com.hatim.qp.security.service.IApiKeyAuthService;
import com.hatim.qp.service.IApiKeyService;
import com.mysql.cj.util.StringUtils;

@Service
public class ApiKeyAuthServiceImpl implements IApiKeyAuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ApiKeyRepository apiKeyRepository;
    @Autowired
    IApiKeyService apiKeyService;

    public String isAuthenticated(String userName, String password, String userApiKey) {
	var user = userRepository.findByUserName(userName);
	if (user.isPresent()) {

	    if (userApiKey == null && StringUtils.isEmptyOrWhitespaceOnly(userApiKey)) {
		if (user.get().getPassword().equals(password)) {
		    return apiKeyService.addApiKey(user.get().getUserId()).toString();
		}
	    }

	    var apiKey = apiKeyRepository.findById(user.get().getUserId()).orElse(null);
	    if (apiKey != null && apiKey.getApiKey().equals(userApiKey)
		    && apiKey.getExpiryDate().before(Calendar.getInstance().getTime())) {
		return apiKey.getApiKey();
	    }
	}
	return null;
    }

    @Override
    public int isAuthenticated(String apiKey) {
	if(apiKey != null) {
	    for(ApiKey key: apiKeyService.getApiKeysMap().values()) {
		if(key.getApiKey().equals(apiKey)) {
		    return key.getUserId();
		}
	    }
	}
	return 0;
    }
}
