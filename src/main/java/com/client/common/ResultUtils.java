package com.client.common;

/**
 *
 * 返回统一数据格式工具类
 *
 * @ClassName: ResultUtils
 * @author: mafangnian
 * @date: 2022/8/2 12:42
 * @Blog: null
 */
public class ResultUtils {

    /**
     * success
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"ok");
    }

    /**
     * error
     * @param errorCode
     * @param <T>
     * @return
     */
    public static <T> BaseResponse error(ErrorCode errorCode){
        return new BaseResponse(errorCode);
    }

    /**
     * error
     * @param errorCode
     * @param message
     * @param description
     * @param <T>
     * @return
     */
    public static <T> BaseResponse error(ErrorCode errorCode,String message,String description){
        return new BaseResponse(errorCode.getCode(),null,message,description);
    }

    /**
     * error
     * @param errorCode
     * @param description
     * @param <T>
     * @return
     */
    public static <T> BaseResponse error(ErrorCode errorCode,String description){
        return new BaseResponse(errorCode.getCode(),description);
    }

    /**
     * error
     * @param code
     * @param message
     * @param description
     * @param <T>
     * @return
     */
    public static <T> BaseResponse error(int code,String message,String description){
        return new BaseResponse(code,null,message,description);
    }
}
