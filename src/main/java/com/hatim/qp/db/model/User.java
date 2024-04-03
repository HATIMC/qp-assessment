package com.hatim.qp.db.model;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2082837211086513057L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "user_type")
    private String userType;

    public int getUserId() {
	return userId;
    }

    public void setUserId(int userId) {
	this.userId = userId;
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

    public String getUserName() {
	return userName;
    }

    public void setUserName(String userName) {
	this.userName = userName;
    }

    public void setPassword(String Password) {
	this.password = Password;
    }

    public String getUserType() {
	return this.userType;
    }

    public void setUserType(String userType) {
	this.userType = userType;
    }

    public String getPassword() {
	return password;
    }

}
