package common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 通用返回对象
 *
 * @author admin
 */
@ApiModel
public class Result<T> implements Serializable {

    @ApiModelProperty(value = "响应码", example = "0")
    private int code;


    @ApiModelProperty(value = "响应消息", example = "成功")
    private String message;

    @ApiModelProperty(value = "逻辑是否成功", example = "true")
    private boolean success;

    @ApiModelProperty(value = "响应的数据")
    private T data;

    public Result() {

    }

    public Result(int code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public Result(int code, String message, boolean success, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public static <T> Result<T> ok() {
        return new Result<T>(ResultStatus.OK.getCode(), ResultStatus.OK.getMessage(), true, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<T>(ResultStatus.OK.getCode(), ResultStatus.OK.getMessage(), true, data);
    }

    public static <T> Result<T> error(IResultStatus status, String message, boolean success) {
        return new Result<T>(status.getCode(), message, success);
    }

    public static <T> Result<T> error(String message) {
        return new Result<T>(-1, message, false);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
