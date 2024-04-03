package com.hatim.qp.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AddNewGroceryItemsResponse {

    @JsonProperty("success")
    private List<GroceryPanel> success;

    @JsonProperty("errors")
    private List<GroceryPanel> errors;

}
