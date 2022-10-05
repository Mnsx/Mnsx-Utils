package top.mnsx.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: blog-mnsx_x
 * @User: Mnsx_x
 * @CreateTime: 2022/10/4 13:04
 * @Description: 相应返回Data类型
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultData {
    private Object data;
    private Long total;
    private Integer pages;
}
