package com.hatim.qp.controller.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hatim.qp.controller.AdminController;
import com.hatim.qp.db.model.User;
import com.hatim.qp.service.IUserService;

@RestController

public class UserController extends AdminController {

    @Autowired
    IUserService userService;
    
    @PostMapping("/adduser")
    @Deprecated
    public User addUser() {
	User user = new User();
	user.setUserName("hatim");
	user.setPassword("hatim");
	user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
	user.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
	user.setUserType("admin");
	return userService.saveUser(user);
    }
    
}
