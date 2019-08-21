package com.hk.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 胡冉
 * @Description: 返回参数
 * @date 2018/11/1219:41
 * @copyright {@link www.hndfsj.com}
 */
@Data
@AllArgsConstructor
@Accessors(chain = true)
public class ApiResponse {

    /**
     * 0表示成功，其它详见错误码说明
     */
    private String errorCode;
    /**
     * 错误消息
     */
    private String errorMessage;
    /**
     * 返回数据  Map
     */
    private Object data;


}
