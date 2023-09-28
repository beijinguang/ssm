package com.idea4j.web.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisCacheDao<T> {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final JedisPool jedisPool;

    public RedisCacheDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    public T get(Class<T> clazz, long id) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = clazz.getName() + ":" + id;
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    RuntimeSchema<T> schema = RuntimeSchema.createFrom(clazz);
                    T t = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, t, schema);
                    return t;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public String put(T t, long id) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = t.getClass().getName() + ":" + id;
                RuntimeSchema schema = RuntimeSchema.createFrom(t.getClass());
                byte[] bytes = ProtostuffIOUtil.toByteArray(t, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存
                int timeout = 60 * 60;//1小时
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

}
