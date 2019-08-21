package com.hk.common.globalweb;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 胡冉
 * @Description: 全局异常捕获
 * @date 2018/11/1213:20
 */
@ControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {
    /**
     * 未授权异常处理
     * @author yangwk
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Map<String, Object> unauthorizedHandler(HttpServletRequest req, HttpServletResponse response, Exception e) {
        // 打印异常信息：
        response.setStatus(403);

        Map<String, Object> map = Maps.newHashMap();
        map.put("code", "-10000");
        map.put("msg", "您没有访问权限");
        e.printStackTrace();
        return map;
    }
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Map<String, Object> defaultErrorHandler(HttpServletRequest req,HttpServletResponse response, Exception e) {
        // 打印异常信息：
        log.error("defaultErrorHandler:", e);
        response.setStatus(500);

        Map<String, Object> map = Maps.newHashMap();
        map.put("code", "-10000");
        map.put("msg", "系统繁忙");
        return map;
    }
}
