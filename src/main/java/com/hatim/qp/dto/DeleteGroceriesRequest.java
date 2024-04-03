package com.hatim.qp.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public class DeleteGroceriesRequest {

    @JsonProperty("grocery_ids")
    private List<Integer> groceryIds;

    public List<Integer> getGroceryIds() {
	return groceryIds;
    }

    public void setGroceryIds(List<Integer> groceryIds) {
	this.groceryIds = groceryIds;
    }

}