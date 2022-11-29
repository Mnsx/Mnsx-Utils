package top.mnsx.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 * 对象属性赋值工具类
 */
public class BeanCopyUtils {

    private BeanCopyUtils() {}

    /**
     * 赋值单个对象的属性
     * @param source 被赋值对象
     * @param clazz 目标类
     * @param <T> 目标类泛型
     * @return 返回复制后的对象
     */
    public static <T> T copyBean(Object source, Class<T> clazz) {
        // 创建目标对象
        T result = null;
        try {
            result = clazz.newInstance();
            // 实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        // 返回结果
        return result;
    }

    /**
     * 将集合中的所有对象复制成为一个新的集合
     * @param source 被复制的集合
     * @param clazz 目标类
     * @param <T> 被复制的集合的泛型
     * @param <V> 目标类泛型
     * @return 返回复制后的对象集合
     */
    public static <T, V> List<T> copyBeanList(List<V> source, Class<T> clazz) {
        return source.stream()
                .map(s -> copyBean(s, clazz))
                .collect(Collectors.toList());
    }
}
