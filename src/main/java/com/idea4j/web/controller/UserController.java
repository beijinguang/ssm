package com.idea4j.web.controller;

import com.idea4j.web.entity.User;
import com.idea4j.web.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author wangjinguang
 * @date 16/8/1
 */
@Controller
@Api(value = "用户管理" ,description = "增加，删除，修改，查找")
@RequestMapping(value = "/user")
@Slf4j
public class UserController {
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ApiOperation(notes = "findUserById", httpMethod = "GET", value = "获取我可以访问的群组的列表")
    public String findUserById(@PathVariable("id")long id, Model model){
        log.info("userId=={}",id);
        User user = userService.findById(id);
        model.addAttribute("user",user);
        return "user/user-detail";
    }

}
