package com.idea4j.service;

import com.idea4j.entity.User;
import org.springframework.stereotype.Service;

/**
 * Created by wangjinguang on 16/8/1.
 */
public interface UserService {

    User findById(long id);
}
