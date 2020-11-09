package com.pkgs.bullshit.entity.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.pkgs.bullshit.enums.ResponseCodeEnum;

import java.util.Date;

import lombok.Data;

/**
 * @author huapeng.huang
 * @version V1.0
 * @since 2020-11-09 17:23
 */
@Data
public class BasicResponse {

    private int code;
    private String message;
    private Object value;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    public static BasicResponse success(Object value) {
        BasicResponse response = new BasicResponse();
        response.setCode(ResponseCodeEnum.SUCCESS.getValue());
        response.setMessage("");
        response.setValue(value);
        response.setTime(new Date());
        return response;
    }

    public static BasicResponse failure(String message) {
        BasicResponse response = new BasicResponse();
        response.setCode(ResponseCodeEnum.FAILURE.getValue());
        response.setMessage(message);
        response.setValue(null);
        response.setTime(new Date());
        return response;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
