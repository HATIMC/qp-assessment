package com.hatim.qp.security.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hatim.qp.db.model.ApiKey;
import com.hatim.qp.db.repository.ApiKeyRepository;
import com.hatim.qp.service.IApiKeyService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiKeyAuthScheduler {

    @Autowired
    ApiKeyRepository apiKeyRepository;

    @Autowired
    IApiKeyService apiKeyService;

    @Scheduled(cron = "0 * * * * *") // Executes every 1 minute(s)
    @Transactional
    public void executeScheduledTask() {
	Map<Integer, ApiKey> tempApiKeys = new HashMap<>();
	apiKeyRepository.findAll().stream().forEach(apikey -> {
	    if (apikey.getExpiryDate().before(Calendar.getInstance().getTime())) {
		apiKeyRepository.delete(apikey);
	    } else {
		tempApiKeys.put(apikey.getUserId(), apikey);
	    }
	});
	apiKeyService.setApiKeysMap(tempApiKeys);
	log.info("ApiKey task executed at: " + new Date());
    }
}
