package com.idea4j.web;

import com.idea4j.entity.User;
import com.idea4j.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangjinguang on 16/8/1.
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/{id}")
    public String findUserById(@PathVariable("id")long id){
        User user = userService.findById(id);
        return "user/user-detail";
    }
}
