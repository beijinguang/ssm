package com.idea4j.web.controller;

import com.idea4j.web.entity.User;
import com.idea4j.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjinguang on 16/8/1.
 */
@Controller
@Api(value = "用户管理" ,description = "增加，删除，修改，查找")
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(notes = "findUserById", httpMethod = "GET", value = "获取我可以访问的群组的列表")
    public String findUserById(@PathVariable("id")long id, Model model){
        log.info(id+"");
        User user = userService.findById(id);
        model.addAttribute("user",user);
        return "user/user-detail";
    }

    public static void main(String[] args) {
        System.out.print("dddddd");
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        for (Integer i :  list) {
            System.out.println(i);
        }
    }
}
