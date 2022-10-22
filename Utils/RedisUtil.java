package top.mnsx.mnsx_book.utils;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import top.mnsx.mnsx_book.entity.Shop;
import top.mnsx.mnsx_book.exception.ShopNotExistException;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static top.mnsx.mnsx_book.utils.RedisConstants.CACHE_SHOP_TTL;

/**
 * @BelongsProject: mnsx_book
 * @User: Mnsx_x
 * @CreateTime: 2022/10/19 13:24
 * @Description: 针对缓存击穿和缓存穿透问题
 */
@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    private static final String CACHE = "CACHE";

    private static final String LOCK = "LOCK";

    private static final Long CACHE_NULL_TTL = 2L;

    public <T> String queryWithProtect(Long id, Long ttl, Class<T> type,
                                       Class<? extends RuntimeException> exception, Function<Long, T> function) {
        String key = CACHE + type.getName() + id;
        // 从redis中查询缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        // 判断redis中是否有缓存
        if (StrUtil.isNotBlank(json)) {
            // 存在，需要将json反序列化为对象
            RedisData redisData = JSON.parseObject(json, RedisData.class);
            if (redisData == null) {
                try {
                    throw exception.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            T obj = (T) redisData.getData();
            LocalDateTime expireTime = redisData.getExpireTime();
            // 判断是否过期
            if (expireTime.isAfter(LocalDateTime.now())) {
                // 未过期直接返回
                return JSON.toJSONString(ResultMap.ok(obj));
            }
            String lockKey = LOCK + type.getName() + id;
            // 已过期，需要缓存重建
            // 获取互斥锁
            boolean ifLock = tryLock(lockKey);
            // 判断是否获取成功
            if (ifLock) {
                // 成功，开启独立线程，重建缓存
                CACHE_REBUILD_EXECUTOR.submit(() -> {
                    try {
                        // 缓存重建
                        this.saveRedis(id, ttl, key, function);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        // 释放锁
                        unlock(lockKey);
                    }
                });
            }
            return JSON.toJSONString(ResultMap.ok(obj));
        }
        // 没有缓存判断命中的是否为空值
        if (json != null) {
            // 返回一个错误信息
            try {
                throw exception.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 不存在，根据id查询数据库
        T obj = function.apply(id);
        // 不存在 返回错误
        if (obj == null) {
            // 将空值传入redis中
            stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            // 返回错误信息
            try {
                throw exception.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 存在 写入redis
        RedisData redisData = new RedisData().setData(obj)
                .setExpireTime(LocalDateTime.now().plusMinutes(ttl));
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(redisData));
        // 返回
        return JSON.toJSONString(ResultMap.ok(obj));
    }

    private boolean tryLock(String key) {
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", RedisConstants.LOCK_SHOP_TTL, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag);
    }

    private void unlock(String key) {
        stringRedisTemplate.delete(key);
    }

    private <T> void saveRedis(Long id, Long expireSeconds, String key, Function<Long, T> function) {
        // 查询店铺数据
        T obj = function.apply(id);
        // 封装逻辑过期时间
        RedisData redisData = new RedisData();
        redisData.setData(obj);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(expireSeconds));
        // 写入redis中
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(redisData));
    }
}
