package top.mnsx.utils;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import top.mnsx.domain.ResponseResult;
import top.mnsx.domain.vo.RedisVo;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;


/**
 * @BelongsProject: mnsx_book
 * @User: Mnsx_x
 * @CreateTime: 2022/10/19 13:24
 * @Description: 针对缓存击穿和缓存穿透问题
 */
@Component
public class RedisSafeUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);

    private static final String CACHE = "cache:";

    private static final String LOCK = "lock:";

    private static final Long CACHE_NULL_TTL = 2L;

    @SuppressWarnings("unchecked")
    public <T> ResponseResult queryWithProtect(Long id, Long ttl, Class<T> type,
                                        Function<Long, T> function) {
        String key = CACHE + type.getName() + ":" + id;
        // 从redis中查询缓存
        String json = stringRedisTemplate.opsForValue().get(key);
        // 判断redis中是否有缓存
        if (StringUtils.hasText(json)) {
            // 存在，需要将json反序列化为对象
            RedisVo redisData = JSON.parseObject(json, RedisVo.class);
            if (redisData == null) {
                throw new RuntimeException("数据不存在!");
            }
            T obj = (T) redisData.getData();
            LocalDateTime expireTime = redisData.getExpireTime();
            // 判断是否过期
            if (expireTime.isAfter(LocalDateTime.now())) {
                // 未过期直接返回
                return ResponseResult.okResult(obj);
            }
            String lockKey = LOCK + type.getName() + ":" + id;
            // 已过期，需要缓存重建
            // 获取互斥锁
            boolean ifLock = tryLock(lockKey, ttl);
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
            return ResponseResult.okResult(obj);
        }
        // 没有缓存判断命中的是否为空值
        if (json != null) {
            // 返回一个错误信息
            throw new RuntimeException("请勿提交空值!");
        }
        // 不存在，根据id查询数据库
        T obj = function.apply(id);
        // 不存在 返回错误
        if (obj == null) {
            // 将空值传入redis中
            stringRedisTemplate.opsForValue().set(key, "", CACHE_NULL_TTL, TimeUnit.MINUTES);
            // 返回错误信息
            throw new RuntimeException("数据不存在!");
        }
        // 存在 写入redis
        RedisVo redisData = new RedisVo().setData(obj)
                .setExpireTime(LocalDateTime.now().plusMinutes(ttl));
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(redisData));
        // 返回
        return ResponseResult.okResult(obj);
    }

    private boolean tryLock(String key, Long expireSeconds) {
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", expireSeconds, TimeUnit.SECONDS);
        return Boolean.TRUE.equals(flag);
    }

    private void unlock(String key) {
        stringRedisTemplate.delete(key);
    }

    private <T> void saveRedis(Long id, Long expireSeconds, String key, Function<Long, T> function) {
        // 查询数据
        T obj = function.apply(id);
        // 封装逻辑过期时间
        RedisVo redisData = new RedisVo();
        redisData.setData(obj);
        redisData.setExpireTime(LocalDateTime.now().plusSeconds(expireSeconds));
        // 写入redis中
        stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(redisData));
    }
}
