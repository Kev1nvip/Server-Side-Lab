package com.stu212306102.helloserver.common;

// 泛型统一响应体，适配任意数据类型的返回
public class Result<T> {
    // 状态码：200成功，500失败
    private Integer code;
    // 提示信息
    private String msg;
    // 核心返回数据
    private T data;

    // 私有构造，仅通过静态方法调用
    private Result() {}
    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 成功响应：无数据返回
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    // 成功响应：带数据返回
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    // 失败响应：自定义错误信息
    public static <T> Result<T> error(String msg) {
        return new Result<>(500, msg, null);
    }

    // 失败响应：自定义状态码+错误信息
    public static <T> Result<T> error(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }

    // Getter和Setter方法
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}