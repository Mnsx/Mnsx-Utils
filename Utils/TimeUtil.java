package top.mnsx.sks.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @BelongsProject: second_kill_system
 * @User: Mnsx_x
 * @CreateTime: 2022/9/23 17:39
 * @Description: 时间工具类
 */
public class TimeUtil {
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime parse(String time) {
        System.out.println(time);
        return LocalDateTime.parse(time, FORMATTER);
    }

    public static String toString(LocalDateTime time) {
        return FORMATTER.format(time);
    }
}
