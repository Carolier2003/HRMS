package com.hr.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
    private Integer code;
    private String msg;
    private T data;
    
    public static <T> R<T> ok() {
        return new R<>(200, "操作成功", null);
    }
    
    public static <T> R<T> ok(T data) {
        return new R<>(200, "操作成功", data);
    }
    
    public static <T> R<T> ok(String msg, T data) {
        return new R<>(200, msg, data);
    }
    
    public static <T> R<T> error() {
        return new R<>(500, "操作失败", null);
    }
    
    public static <T> R<T> error(String msg) {
        return new R<>(500, msg, null);
    }
    
    public static <T> R<T> error(Integer code, String msg) {
        return new R<>(code, msg, null);
    }
    
    public static <T> R<T> forbidden() {
        return new R<>(403, "权限不足", null);
    }
    
    public static <T> R<T> forbidden(String msg) {
        return new R<>(403, msg, null);
    }
    
    public static <T> R<T> badRequest(String msg) {
        return new R<>(400, msg, null);
    }
}

