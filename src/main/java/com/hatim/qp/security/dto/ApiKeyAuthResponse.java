package com.hatim.qp.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Setter;

@Setter
@Builder
public class ApiKeyAuthResponse {

    @JsonProperty("api_key")
    String apiKey;
}
