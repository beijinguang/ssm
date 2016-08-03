package com.idea4j.web.service.impl;

import com.idea4j.web.dao.UserDAO;
import com.idea4j.web.entity.User;
import com.idea4j.web.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by markee on 2016/8/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class UserServiceCacheImplTest {
    @Autowired
    @Qualifier("userServiceImpl")
    UserService userService;
    @Test
    public void findById() throws Exception {
        User user = userService.findById(1);
        assertEquals(user.getName(),"123");
    }

}