package com.hk.dto;

import com.hk.domain.CamerasEx;
import com.hk.domain.Encoders;
import lombok.Data;

import java.util.List;

/**
 * @author 胡冉
 * @Description: TODO
 * @date 2018/11/1310:34
 * @copyright {@link www.hndfsj.com}
 */
@Data
public class JsonCamerasExDTO {
    private int total;
    private int pageNo;
    private int pageSize;
    private List<CamerasEx> list;
}
