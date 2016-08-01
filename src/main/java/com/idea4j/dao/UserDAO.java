package com.idea4j.dao;

import com.idea4j.entity.User;

/**
 * Created by wangjinguang on 16/8/1.
 */

public interface UserDAO {
    User findById(long id);
}
