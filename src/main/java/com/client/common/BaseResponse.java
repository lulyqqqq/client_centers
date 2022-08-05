package com.client.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: BaseRespone
 * @author: mafangnian
 * @date: 2022/8/2 12:30
 * @Blog: null
 */
@Data
public class BaseResponse<T> implements Serializable {
    private String message;
    private int code;
    private T data;
    private String description;

    public BaseResponse(int code,T data,String message,String description){
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data,String message){
        this(code,data,message,"");
    }

    public BaseResponse(int code, T data){
        this(code,data,"","");
    }
    public  BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDescription());
    }

}
