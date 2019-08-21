package com.hk.dto;

import com.hk.domain.Encoders;
import lombok.Data;

import java.util.List;

/**
 * @author 胡冉
 * @Description: TODO
 * @date 2018/11/1310:16
 * @copyright {@link www.hndfsj.com}
 */
@Data
public class JsonEncodersDTO {
    private int total;
    private int pageNo;
    private int pageSize;
    private List<Encoders> list;
}
