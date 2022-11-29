package top.mnsx.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class RedisVo {
    private LocalDateTime expireTime;
    private Object data;
}
