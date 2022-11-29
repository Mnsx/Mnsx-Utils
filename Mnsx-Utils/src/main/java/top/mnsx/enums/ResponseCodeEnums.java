package top.mnsx.enums;

/**
 * @Author Mnsx_x xx1527030652@gmail.com
 * 响应枚举类
 */
public enum ResponseCodeEnums {
    // 成功
    SUCCESS(200,"操作成功");

    private final int code;
    private final String msg;

    ResponseCodeEnums(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
