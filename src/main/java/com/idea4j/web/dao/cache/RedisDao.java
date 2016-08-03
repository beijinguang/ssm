package com.idea4j.web.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.idea4j.web.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 处理user的缓存类
 */
@Deprecated
public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JedisPool jedisPool;
    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }
    private RuntimeSchema<User> schema = RuntimeSchema.createFrom(User.class);
    public User getUser(long id) {
        //redis操作逻辑
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "user:" + id;
                //并没有实现内部序列化操作get-> byte[] -> 反序列化 ->Object(user)
                // 采用自定义序列化 protostuff : pojo.
                byte[] bytes = jedis.get(key.getBytes());
                //缓存中获取到bytes
                if (bytes != null) {
                    //空对象
                    User user = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, user, schema);
                    //user 被反序列化
                    return user;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String putUser(User user) {
        // set Object(user) -> 序列化 -> byte[]
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "user:" + user.getId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(user, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存
                int timeout = 60 * 60;//1小时
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
