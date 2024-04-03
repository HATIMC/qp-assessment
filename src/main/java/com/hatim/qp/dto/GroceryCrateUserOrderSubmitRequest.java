package com.hatim.qp.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroceryCrateUserOrderSubmitRequest {

    @JsonProperty("grocery_id")
    private Integer groceryId;

    @JsonProperty("level")
    private Integer level;

    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	GroceryCrateUserOrderSubmitRequest grocery = (GroceryCrateUserOrderSubmitRequest) o;
	return Objects.equals(this.groceryId, grocery.getGroceryId());
    }

    @Override
    public int hashCode() {
	return Objects.hash(this.groceryId);
    }

    public Integer getGroceryId() {
	return groceryId;
    }

    public void setGroceryId(Integer groceryId) {
	this.groceryId = groceryId;
    }

    public Integer getLevel() {
	return level;
    }

    public void setLevel(Integer level) {
	this.level = level;
    }

}
