package com.hatim.qp.service;

import com.hatim.qp.db.model.User;

public interface IUserService {

    User saveUser(User user);

    User findByUsername(String username);

}