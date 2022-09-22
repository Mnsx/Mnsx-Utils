package top.mnsx.second_kill_system.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: second_kill_system
 * @User: Mnsx_x
 * @CreateTime: 2022/9/22 19:58
 * @Description: 响应码
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultCode {
    private int code;
    private String message;

    public static ResultCode SUCCESS = new ResultCode(0, "success");
}
