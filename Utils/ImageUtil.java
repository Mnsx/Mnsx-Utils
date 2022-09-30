package top.mnsx.sks.utils;

import org.springframework.web.multipart.MultipartFile;
import top.mnsx.sks.exception.ImageIOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.UUID;

/**
 * @BelongsProject: second_kill_system
 * @User: Mnsx_x
 * @CreateTime: 2022/9/30 9:24
 * @Description: 图片工具类
 */
public class ImageUtil {
    public static String saveImg(MultipartFile multipartFile, String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        String fileName = UUID.randomUUID() + ".png";

        path = path + File.separator + fileName;

        try (
                FileChannel from = ((FileInputStream) multipartFile.getInputStream()).getChannel();
                FileChannel to = new FileOutputStream(path).getChannel();
        ) {
            from.transferTo(0, from.size(), to);
        } catch (IOException e) {
            throw new ImageIOException();
        }

        return fileName;
    }

    public static boolean delImg(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }

        return file.delete();
    }
}
