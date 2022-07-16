package com.response;

/**
 * 响应 枚举
 *
 * @author admin
 */
public enum ResultStatus implements IResultStatus {

    /**
     * 状态
     */
    OK(0, "成功"),

    BAD_REQUEST(1, "错误请求"),


    ;


    /**
     * 状态码,长度固定为6位的字符串.
     */
    private final int code;
    /**
     * 错误信息.
     */
    private final String message;

    ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
