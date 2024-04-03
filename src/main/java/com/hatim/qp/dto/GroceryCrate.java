package com.hatim.qp.dto;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroceryCrate {

    @JsonProperty("grocery_id")
    private Integer groceryId;

    @JsonProperty("grocery_name")
    private String groceryName;

    @JsonProperty("level")
    private Integer level;

    @JsonProperty("grocery_price")
    private Double groceryPrice;

    public boolean equals(Object o) {
	if (this == o)
	    return true;
	if (o == null || getClass() != o.getClass())
	    return false;
	GroceryCrate grocery = (GroceryCrate) o;
	return Objects.equals(this.groceryName, grocery.getGroceryName()) || this.groceryId == grocery.getGroceryId();
    }

    @Override
    public int hashCode() {
	return Objects.hash(this.groceryName + this.groceryId);
    }

    public Integer getGroceryId() {
	return groceryId;
    }

    public void setGroceryId(Integer groceryId) {
	this.groceryId = groceryId;
    }

    public String getGroceryName() {
	return groceryName;
    }

    public void setGroceryName(String groceryName) {
	this.groceryName = groceryName;
    }

    public Integer getLevel() {
	return level;
    }

    public void setLevel(Integer level) {
	this.level = level;
    }

    public Double getGroceryPrice() {
	return groceryPrice;
    }

    public void setGroceryPrice(Double groceryPrice) {
	this.groceryPrice = groceryPrice;
    }

}
