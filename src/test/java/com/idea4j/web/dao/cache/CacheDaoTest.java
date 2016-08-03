package com.idea4j.web.dao.cache;

import com.idea4j.web.dao.UserDAO;
import com.idea4j.web.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by markee on 2016/8/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class CacheDaoTest {
    private long id = 1L;

    @Autowired
    RedisCacheDao<User> redisCacheDao;
    @Autowired
    UserDAO userDAO;

    @Test
    public void get() throws Exception {
        User user = redisCacheDao.get(User.class, id);
        System.out.println(user.getName());
    }

    @Test
    public void put() throws Exception {
        User user = userDAO.findById(1L);
        redisCacheDao.put(user, user.getId());
    }

}