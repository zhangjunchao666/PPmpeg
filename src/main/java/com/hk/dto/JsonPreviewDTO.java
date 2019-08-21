package com.hk.dto;

import lombok.Data;

/**
 * @author 胡冉
 * @Description: TODO
 * @date 2018/11/1316:07
 * @copyright {@link www.hndfsj.com}
 */
@Data
public class JsonPreviewDTO {
    private int errorCode;
    private String errorMessage;
    private DateResult data;
    @Data
    public class DateResult{
        private String previewXml;
    }

    public static void main(String[] args) {

    }
}
