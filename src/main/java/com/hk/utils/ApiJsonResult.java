package com.hk.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 胡冉
 * @ClassName ApiJsonResult
 * @Date 2019/5/8 11:27
 * @Version 2.0
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class ApiJsonResult {
    private String code;
    private String message;
    private Object data;
    public ApiJsonResult(String code,String message){
        this.code=code;
        this.message=message;
    }
}
