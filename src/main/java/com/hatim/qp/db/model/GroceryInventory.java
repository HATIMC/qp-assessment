package com.hatim.qp.db.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "grocery_inventory")
public class GroceryInventory implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -356489817041896186L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grocery_id")
    @JsonProperty("grocery_id")
    private int groceryId;

    @Column(name = "created_date")
    @JsonProperty("created_date")
    private Timestamp createdDate;

    @Column(name = "updated_date")
    @JsonProperty("updated_date")
    private Timestamp updatedDate;

    @Column(name = "grocery_name")
    @JsonProperty("grocery_name")
    private String groceryName;

    @Column(name = "grocery_price")
    @JsonProperty("grocery_price")
    private double groceryPrice;

    @Column(name = "is_deleted")
    @JsonProperty("deleted")
    private boolean deleted;

    public int getGroceryId() {
	return groceryId;
    }

    public void setGroceryId(int groceryId) {
	this.groceryId = groceryId;
    }

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

    public double getGroceryPrice() {
	return groceryPrice;
    }

    public void setGroceryPrice(double groceryPrice) {
	this.groceryPrice = groceryPrice;
    }

    public boolean getDeleted() {
	return deleted;
    }

    public void setDeleted(boolean deleted) {
	this.deleted = deleted;
    }

}
