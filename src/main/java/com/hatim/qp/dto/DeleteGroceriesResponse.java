package com.hatim.qp.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class DeleteGroceriesResponse {

    @JsonProperty("success")
    private List<GroceryPanel> success;

    @JsonProperty("errors")
    private List<Integer> errors;

}