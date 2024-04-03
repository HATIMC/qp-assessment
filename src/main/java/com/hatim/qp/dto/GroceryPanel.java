package com.hatim.qp.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroceryPanel {
    @JsonProperty("grocery_id")
    private Integer groceryId;
    @JsonProperty("created_date")
    private Timestamp createdDate;
    @JsonProperty("updated_date")
    private Timestamp updatedDate;
    @JsonProperty("grocery_name")
    private String groceryName;
    @JsonProperty("grocery_price")
    private Double groceryPrice;
    @JsonProperty("level")
    private Integer level;
    @JsonProperty("deleted")
    private boolean deleted;

    public Timestamp getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
	this.createdDate = createdDate;
    }

    public Timestamp getUpdatedDate() {
	return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
	this.updatedDate = updatedDate;
    }

    public String getGroceryName() {
	return groceryName;
    }

    public void setGroceryName(String groceryName) {
	this.groceryName = groceryName;
    }

    public Double getGroceryPrice() {
	return groceryPrice;
    }

    public void setGroceryPrice(Double groceryPrice) {
	this.groceryPrice = groceryPrice;
    }

    public Integer getLevel() {
	return level;
    }

    public void setLevel(Integer level) {
	this.level = level;
    }

    public Integer getGroceryId() {
	return groceryId;
    }

    public void setGroceryId(Integer groceryId) {
	this.groceryId = groceryId;
    }

    public boolean isDeleted() {
	return deleted;
    }

    public void setDeleted(boolean deleted) {
	this.deleted = deleted;
    }

}
