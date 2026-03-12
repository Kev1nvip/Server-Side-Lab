package com.stu212306102.helloserver.exception;

import com.stu212306102.helloserver.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// 全局异常处理器，拦截所有@RestController的异常
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理所有运行时异常（通用异常）
    @ExceptionHandler(RuntimeException.class)
    public Result<String> handleRuntimeException(RuntimeException e) {
        // 返回统一错误响应，携带异常信息
        return Result.error("服务器运行时异常：" + e.getMessage());
    }

    // 处理算术异常（如除数为0，用于测试）
    @ExceptionHandler(ArithmeticException.class)
    public Result<String> handleArithmeticException(ArithmeticException e) {
        return Result.error("算术异常：" + e.getMessage());
    }

    // 处理所有异常（兜底）
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        return Result.error(500, "服务器未知异常：" + e.getMessage());
    }
}