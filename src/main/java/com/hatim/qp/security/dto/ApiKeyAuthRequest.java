package com.hatim.qp.security.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiKeyAuthRequest {

    @JsonProperty("username")
    @JsonAlias("username")
    String username;

    @JsonProperty("password")
    @JsonAlias("password")
    String password;

}
