package top.mnsx.mnsxutils.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: mnsx-utils
 * @User: Mnsx_x
 * @CreateTime: 2022/9/22 19:58
 * @Description: 响应码类——用来保存响应请求的响应码和响应消息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultCode {
    // 响应码
    private int code;
    // 响应消息
    private String message;

    // 成功响应
    public static ResultCode SUCCESS = new ResultCode(0, "success");
    // 错误响应
}
