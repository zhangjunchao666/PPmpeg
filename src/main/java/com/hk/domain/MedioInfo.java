package com.hk.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author 胡冉
 * @Description: 根据监控点UUID、网域UUID获取预览参数（XML报文），
 * 用于传给OCX控件，实现视频预览
 * @date 2018/11/1316:26
 * @copyright {@link www.hndfsj.com}
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "HK_MEDIO_INFO")
public class MedioInfo implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 50)
    private Long id;
    @Column(name = "address", length = 1500)
    private String address;
    @Column(name = "cameraName", length = 100)
    private String cameraName;
    @Column(name = "cameraUuid", length = 100)
    private String cameraUuid;
    private Timestamp time;

}
