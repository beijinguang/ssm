package com.idea4j.web.service.impl;

import com.idea4j.web.dao.UserDAO;
import com.idea4j.web.entity.User;
import com.idea4j.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangjinguang on 16/8/1.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Override
    public User findById(long id) {
        return userDAO.findById(id);
    }
}
