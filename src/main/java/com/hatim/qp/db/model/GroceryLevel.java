package com.hatim.qp.db.model;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "grocery_level")
public class GroceryLevel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 613573689591179983L;

    @Id
    @Column(name = "level_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int levelId;

    @OneToOne
    @JoinColumn(name = "grocery_id", nullable = false)
    private GroceryInventory groceryInventory;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @Column(name = "level")
    private int level;
    
    public int getLevelId() {
	return levelId;
    }

    public void setLevelId(int levelId) {
	this.levelId = levelId;
    }

    public GroceryInventory getGroceryInventory() {
	return groceryInventory;
    }

    public void setGroceryInventory(GroceryInventory groceryInventory) {
	this.groceryInventory = groceryInventory;
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

    public int getLevel() {
	return level;
    }

    public void setLevel(int level) {
	this.level = level;
    }

}
