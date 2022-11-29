package top.mnsx.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 */
public class SecurityUtil {
    /**
     * 获取SecurityHolder中的对象数据
     * @param clazz 数据类型
     * @param <T> 泛型参数
     * @return 返回对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getObject(Class<T> clazz)
    {
        return (T) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     * @return Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
