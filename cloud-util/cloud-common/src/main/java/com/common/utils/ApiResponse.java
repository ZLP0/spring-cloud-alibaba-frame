package com.common.utils;

import java.io.Serializable;


public class ApiResponse<T> implements Serializable {

    private static final Integer SUCCESS = 200;

    private static final Integer FAIL = -1;

    //响应数据结果集
    private T data;

    /**
     * 状态码
     * 20000 操作成功
     * 50000 操作失败
     */
    private Integer code;

    /***S
     * 响应信息
     */
    private String message;

    public ApiResponse() {
    }

    public static ApiResponse success() {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(SUCCESS);
        return apiResponse;
    }

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(SUCCESS);
        apiResponse.setData(data);
        return apiResponse;
    }

    public static ApiResponse fail(String msg) {
        ApiResponse apiResponse = new ApiResponse<>();
        apiResponse.setCode(FAIL);
        apiResponse.setMessage(msg);
        return apiResponse;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
