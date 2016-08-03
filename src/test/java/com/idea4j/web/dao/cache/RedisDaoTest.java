//package com.idea4j.web.dao.cache;
//
//import com.idea4j.web.dao.UserDAO;
//import com.idea4j.web.entity.User;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import static org.junit.Assert.*;
//
///**
// * Created by markee on 2016/8/3.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:spring/spring-dao.xml"})
//public class RedisDaoTest {
//    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDaoTest.class);
//
//    private long id = 1L;
//
//    @Autowired
//    private UserDAO userDAO;
//
//    @Autowired
//    private RedisDao redisDao;
//    @Test
//    public void getUser() throws Exception {
//        long start = System.currentTimeMillis();
//        User user = redisDao.getUser(id);
//        if (null == user){
//            user = userDAO.findById(id);
//            if (null!=user){
//                String result = redisDao.putUser(user);
//                LOGGER.info(result);
//                user = redisDao.getUser(id);
//                LOGGER.info(user.toString());
//            }
//        }
//        long end = System.currentTimeMillis();
//        System.out.println(end-start);
//    }
//
//    @Test
//    public void putUser() throws Exception {
//
//    }
//
//}