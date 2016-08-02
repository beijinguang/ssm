package com.idea4j.web.dao;

import com.idea4j.web.entity.User;

/**
 * Created by wangjinguang on 16/8/1.
 */

public interface UserDAO {
    User findById(long id);
}
