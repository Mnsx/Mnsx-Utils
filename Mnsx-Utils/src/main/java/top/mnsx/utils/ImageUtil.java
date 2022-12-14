package top.mnsx.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.UUID;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 * 对象属性赋值工具类
 */
@Slf4j
public class ImageUtil {
    /**
     * 图片保存类
     * @param multipartFile SpringMVC提供的简化上传的工具类
     * @param path 文件保存位置
     * @return 返回文件名（使用UUID随机生成文件名）
     */
    public static String saveImg(MultipartFile multipartFile, String path) {
        // 根据path生成路径
        File file = new File(path);

        // 判断文件路径是否存在
        if (!file.exists()) {
            boolean flag = file.mkdirs();
            if (!flag) {
                log.info("[mnsx-utils/ImageUtil]: 文件夹创建失败");
            }
        }

        // 通过UUID创建文件名（随机生成）
        String fileName = UUID.randomUUID() + ".png";

        // 拼接路径
        path = path + File.separator + fileName;

        // 使用nio进行文件编程
        try (
                FileChannel from = ((FileInputStream) multipartFile.getInputStream()).getChannel();
                FileOutputStream fos = new FileOutputStream(path);
                FileChannel to = fos.getChannel();
        ) {
            from.transferTo(0, from.size(), to);
        } catch (IOException e) {
            log.debug("[mnsx-utils/ImageUtil]: 文件传输过程中抛出IOException");
        }

        return fileName;
    }
}
