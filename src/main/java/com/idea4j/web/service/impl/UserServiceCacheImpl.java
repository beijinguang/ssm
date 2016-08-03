package com.idea4j.web.service.impl;

import com.idea4j.web.dao.UserDAO;
import com.idea4j.web.dao.cache.RedisCacheDao;
import com.idea4j.web.dao.cache.RedisDao;
import com.idea4j.web.entity.User;
import com.idea4j.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangjinguang on 16/8/1.
 */
@Service
public class UserServiceCacheImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RedisCacheDao<User> redisCacheDao;

    @Override
    public User findById(long id) {
        User user = redisCacheDao.get(User.class,id);
        if (user == null){
            user = userDAO.findById(id);
            redisCacheDao.put(user,user.getId());
        }
        return user;
    }
}
