package com.hatim.qp.service;

import java.util.Map;
import java.util.UUID;

import com.hatim.qp.db.model.ApiKey;

public interface IApiKeyService {
    UUID addApiKey(int userId);

    Map<Integer, ApiKey> getApiKeysMap();

    void setApiKeysMap(Map<Integer, ApiKey> apikeyMap);
}
