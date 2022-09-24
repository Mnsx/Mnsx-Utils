package top.mnsx.sks.utils;

import lombok.val;
import org.springframework.util.DigestUtils;

import java.util.Random;

/**
 * @BelongsProject: second_kill_system
 * @User: Mnsx_x
 * @CreateTime: 2022/9/22 15:55
 * @Description: MD5加密解密工具类
 */
public class MD5Util {
    public static String md5(String src) {
        return DigestUtils.md5DigestAsHex(src.getBytes());
    }

    private static final String salt = "7655d825";

    public static String inputPassToMidPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(0) + salt.charAt(3);
        return md5(str);
    }

    private static String midPassToTPass(String midPass, String tSalt) {
        String str = "" + tSalt.charAt(0) + tSalt.charAt(2) + midPass + tSalt.charAt(0) + tSalt.charAt(3);
        return md5(str);
    }

    public static String inputPassToTPass(String inputPass, String tSalt) {
        String midPass = inputPassToMidPass(inputPass);
        return midPassToTPass(midPass, tSalt);
    }

    public static String getStringRandom(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            int chatType = random.nextInt(3);
            switch (chatType){
                case 0:
                    // 生成数字
                    sb.append(random.nextInt(10));
                    break;
                case 1:
                    // 生成小写字母
                    sb.append((char) (random.nextInt(26) + 97));
                    break;
                case 2:
                    // 生成大写字母
                    sb.append((char) (random.nextInt(26) + 65));
                    break;
            }
        }
        return sb.toString();
    }
}
