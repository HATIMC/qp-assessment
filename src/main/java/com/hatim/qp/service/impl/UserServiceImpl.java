package com.hatim.qp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hatim.qp.db.model.User;
import com.hatim.qp.db.repository.UserRepository;
import com.hatim.qp.service.IUserService;
import com.hatim.qp.utils.QpUtils;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
	if (user.getUserId() == 0) {
	    user.setCreatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
	    user.setUpdatedDate(new java.sql.Timestamp(System.currentTimeMillis()));
	}
	return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
	User user = userRepository.findByUserName(username).orElse(null);
	if (null != user) {
	    user.setPassword(QpUtils.decodeString(user.getPassword()));
	}
	return user;
    }

    
    
    
    
    

}