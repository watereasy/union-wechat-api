package com.jd.union.wechat.api.base;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Response<T> implements Serializable{

    private static final long serialVersionUID = -6213952280403198804L;

    @Getter @Setter private Integer code;

    @Getter @Setter private String message;

    @Getter @Setter private T result;

    public void success(){
        this.code = 0;
        this.message = "ok";
    }

    public void success(T result){
        success();
        this.result = result;
    }

    public void success(Integer code, String message, T result){
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public void fail(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
