package com.hatim.qp.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hatim.qp.db.model.ApiKey;
import com.hatim.qp.db.repository.ApiKeyRepository;
import com.hatim.qp.service.IApiKeyService;
import com.hatim.qp.utils.QpUtils;

import jakarta.annotation.PostConstruct;

@Service
public class ApiKeyServiceImpl implements IApiKeyService {

    @Autowired
    ApiKeyRepository apiKeyRepository;

    @Value("${qp.apikey.expiration.days}")
    Integer expirationDays;

    private Map<Integer, ApiKey> apiKeys = new HashMap<>();

    @PostConstruct
    public void init() {
	List<ApiKey> dbKeys = apiKeyRepository.findAll();
	for (ApiKey apiKey : dbKeys) {
	    apiKeys.put(apiKey.getUserId(), apiKey);
	}
    }

    public UUID addApiKey(int userId) {
	if (getApiKeysMap().keySet().contains(userId)) {
	    return UUID.fromString(getApiKeysMap().get(userId).getApiKey());
	}

	ApiKey apiKey = new ApiKey();
	apiKey.setApiKey(UUID.randomUUID().toString());
	apiKey.setUserId(userId);
	apiKey.setCreatedDate(Calendar.getInstance().getTime());
	apiKey.setExpiryDate(QpUtils.addDaysToDate(apiKey.getCreatedDate(), expirationDays));
	apiKeys.put(userId, apiKeyRepository.save(apiKey));
	return UUID.fromString(apiKeys.get(userId).getApiKey());
    }

    public Map<Integer, ApiKey> getApiKeysMap() {
	return this.apiKeys;
    }

    public void setApiKeysMap(Map<Integer, ApiKey> apikeyMap) {
	this.apiKeys = apikeyMap;
    }
}
