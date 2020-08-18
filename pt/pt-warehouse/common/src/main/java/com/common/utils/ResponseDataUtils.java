package com.common.utils;

import com.common.enums.ResultCodeEnums;

public class ResponseDataUtils {
    /**
     * 带实体的统一返回
     *
     * @param data 实体
     * @param <T>  实体类型
     * @return
     */
    public static <T> ResponseData buildSuccess(T data, String msg) {
        return new ResponseData<T>(ResultCodeEnums.SUCCESS.getCode(), msg, data);
    }

    public static <T> ResponseData buildError(T data, String msg) {
        return new ResponseData(ResultCodeEnums.ERROR.getCode(), msg, data);
    }

}
