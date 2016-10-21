package com.idea4j.web.controller;

import com.idea4j.web.entity.User;
import com.idea4j.web.service.UserService;
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
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public String findUserById(@PathVariable("id")long id, Model model){
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
