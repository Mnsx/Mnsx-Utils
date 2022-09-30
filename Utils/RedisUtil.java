package top.mnsx.sks.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @BelongsProject: background_management_system
 * @User: Mnsx_x
 * @CreateTime: 2022/9/12 14:35
 * @Description: redis工具类
 */
@Component
public final class RedisUtil {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 设置缓存过期时间
     * @param key
     * @param time
     * @return
     */
    public Boolean expire(String key, Long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key，获取过期时间
     * @param key
     * @return
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断可以是否存在
     * @param key
     * @return
     */
    public Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key
     */
    public void del(String key) {
        if (key != null) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 字符缓存获取
     * @param key
     * @return
     */
    public String get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 字符缓存放入
     * @param key
     * @param value
     * @return
     */
    public Boolean set(String key, String value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 字符缓存放入并设置时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public Boolean set(String key, String value, Long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 增加
     * @param key
     * @param offset
     * @return
     */
    public Long incr(String key, Long offset) {
        if (offset < 0) {
            throw new RuntimeException("偏移量必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, offset);
    }

    /**
     * 减少
     * @param key
     * @param offset
     * @return
     */
    public Long decr(String key, Long offset) {
        if (offset < 0) {
            throw new RuntimeException("偏移量必须大于0");
        }
        return redisTemplate.opsForValue().decrement(key, offset);
    }

    /**
     * 获取key对应的值
     * @param key
     * @param item
     * @return
     */
    public Object hGet(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取key对应的所有键值
     * @param key
     * @return
     */
    public Map<Object, Object> hGet(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 根据key设置键值对
     * @param key
     * @param map
     * @return
     */
    public Boolean hSet(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置hash并且设置时间
     * @param key
     * @param map
     * @param time
     * @return
     */
    public Boolean hSet(String key, Map<String, Object> map, Long time)  {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据，如果不存在将创建
     * @param key
     * @param item
     * @param value
     * @return
     */
    public Boolean hSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据，如果不存在将创建，并且设置过期时间
     * @param key
     * @param item
     * @param value
     * @param time
     * @return
     */
    public Boolean hSet(String key, String item, Object value, Long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除表中的值
     * @param key
     * @param item
     */
    public void hDel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项值
     * @param key
     * @param item
     * @return
     */
    public Boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 增加
     * @param key
     * @param item
     * @param offset
     * @return
     */
    public Double hIncr(String key, String item, Double offset) {
        return redisTemplate.opsForHash().increment(key, item, offset);
    }

    /**
     * 减少
     * @param key
     * @param item
     * @param offset
     * @return
     */
    public Double hDecr(String key, String item, Double offset) {
        return redisTemplate.opsForHash().increment(key, item, -offset);
    }

    /**
     * 根据key获取set中的值
     * @param key
     * @return
     */
    public Set<String> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * value是否存在set中
     * @param key
     * @param value
     * @return
     */
    public Boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set中
     * @param key
     * @param values
     * @return
     */
    public Long sSet(String key, String... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 将set数据放入缓存
     * @param key
     * @param time
     * @param values
     * @return
     */
    public Long sSetAndTime(String key, Long time, String... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取set缓存的长度
     * @param key
     * @return
     */
    public Long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 删除值为value
     * @param key
     * @param values
     * @return
     */
    public Long sRemove(String key, String values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取list缓存的内容
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lGet(String key, Long start, Long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存长度
     * @param key
     * @return
     */
    public Long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 通过索引，获取list中的值
     * @param key
     * @param index
     * @return
     */
    public String lGetIndex(String key, Long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     * @param key
     * @param value
     * @return
     */
    public Boolean lSet(String key, String value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存并设置过期时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public Boolean lSet(String key, String value, Long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key
     * @param value
     * @return
     */
    public Boolean lSet(String key, List<String> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存，并设置过期时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public Boolean lSet(String key, List<String> value, Long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key
     * @param index
     * @param value
     * @return
     */
    public Boolean lUpdateIndex(String key, Long index, String value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Long lRemove(String key, Long count, String value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 添加根据score
     * @param key
     * @param score
     * @param value
     * @return
     */
    public Boolean zSet(String key, double score, String value) {
        try {
            Boolean add = redisTemplate.opsForZSet().add(key, value, score);
            return add;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Set<ZSetOperations.TypedTuple<String>> zGetByScore(String key, Long start, Long end, Boolean flag) {
        try {
            Set<ZSetOperations.TypedTuple<String>> range = null;
            if (flag) {
                range = redisTemplate.opsForZSet().rangeWithScores(key, start, end);
                return range;
            } else {
                range = redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
                return range;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long zDel(String key, Double min, Double max) {
        try {
            Long count = redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
}