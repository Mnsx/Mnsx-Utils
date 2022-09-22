package top.mnsx.sks.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @BelongsProject: background_management_system
 * @BelongsPackage: top.mnsx.background_management_system.utils
 * @CreateTime: 2022/9/10
 * @Description: JWT工具类
 */
public class JWTUtil {
    private static final String TOKEN = "7655d825";

    /**
     * 将map类型转换成token字符串
     * @param map
     * @return
     */
    public static String getToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();
        map.forEach(builder::withClaim);

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);
        builder.withExpiresAt(instance.getTime());
        return builder.sign(Algorithm.HMAC256(TOKEN)).toString();
    }

    /**
     * 验证json字符串
     * @param token
     */
    public static void verify(String token) {
        JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }

    /**
     * 获取json字符串中的信息
     * @param token
     * @return
     */
    public static DecodedJWT getToken(String token) {
        if (token == null) {
            return null;
        }
        return JWT.require(Algorithm.HMAC256(TOKEN)).build().verify(token);
    }
}
