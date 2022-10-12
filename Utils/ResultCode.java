package top.mnsx.token_demo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: mnsx-utils
 * @User: Mnsx_x
 * @CreateTime: 2022/9/22 19:58
 * @Description: 响应码类——用来保存响应请求的响应码和响应消息
 */
public enum ResultCode {
    // 成功响应
    SUCCESS(200, "请求成功"),
    // 错误响应
    // 服务器内部问题
    INNER_ERROR(500, "服务器内部问题，请联系管理员");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
