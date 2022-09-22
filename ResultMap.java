package top.mnsx.sks.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @BelongsProject: second_kill_system
 * @User: Mnsx_x
 * @CreateTime: 2022/9/22 15:05
 * @Description: 响应类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultMap<T> {
    private Integer code;
    private T data;
    private String message;

    ResultMap(Builder builder) {
        this.code = builder.code;
        this.data = builder.data;
        this.message = builder.message;
    }

    private static ResultCod SUCCESS = new ResultCod(0, "success");

    public ResultMap<T> ok(T data) {
        return builder().code(ResultMap.SUCCESS.getCode())
                .message(ResultMap.SUCCESS.getMsg())
                .data(data)
                .build();
    }

    public ResultMap<T> ok() {
        return builder().code(ResultMap.SUCCESS.getCode())
                .message(ResultMap.SUCCESS.getMsg())
                .build();
    }

    public ResultMap<T> fail(ResultCod resultCode) {
        return builder().code(resultCode.getCode())
                .message(resultCode.getMsg())
                .build();
    }

    public ResultMap<T> fail(ResultCod resultCode, T data) {
        return builder().code(resultCode.getCode())
                .message(resultCode.getMsg())
                .data(data)
                .build();
    }

    private Builder builder() {
        return new Builder();
    }

    private class Builder {
        private Integer code;
        private T data;
        private String message;

        public Builder code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public ResultMap<T> build() {
            return new ResultMap<>(this);
        }
    }
}