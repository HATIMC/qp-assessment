package com.hatim.qp.dto;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroceryCrateUserOrderSubmitResponse {

    @JsonProperty("success")
    List<GroceryCrateUserOrderSubmitRequest> success;
    @JsonProperty("error")
    List<GroceryCrateUserOrderSubmitRequest> error;

    @JsonProperty("price")
    Double price;
}
