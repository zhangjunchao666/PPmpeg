package com.hk.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 胡冉
 * @Description: 编码设备
 * @date 2018/11/1219:25
 * @copyright {@link www.hndfsj.com}
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "HK_ENCODERS")
public class Encoders implements Serializable {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", length = 50)
    private Long id;

    /**
     * 编码设备UUID  是
     */
    @Column(name = "encoderUuid", length = 50)
    private String encoderUuid;

    /**
     * 编码设备名称 是
     */
    @Column(name = "encoderName", length = 50)
    private String encoderName;

    /**
     * 编码设备类型码  是
     */
    private Integer encoderModel;

    /**
     * 设备用户名  是
     */
    @Column(name = "encoderUserName", length = 50)
    private String encoderUserName;

    /**
     * 设备端口   是
     */
    @Column(name = "encoderPort", length = 50)
    private String encoderPort;

    /**
     * 设备IP 是
     */
    private String encoderIp;
    /**
     * 专业智能类型码 否
     */
    @Column(name = "smartType", length = 50)
    private String smartType;
    /**
     * 是否支持智能 否
     */
    private Integer smartSupport;
    /**
     * 海康设备类型码 否
     */
    @Column(name = "devType", length = 50)
    private String devType;

    /**
     * 排序字段  否
     */
    private Integer orderNum;
    /**
     *中心UUID 否
     */
    @Column(name = "unitUuid", length = 50)
    private String unitUuid;
    /**
     *更新时间 是

     */
    private Long updateTime;
    /**
     *报警输入数  是

     */
    private Integer alarmIn;
    /**
     *报警输出数  是

     */
    private Integer alarmOut;
    /**
     *可视对讲通道数  是
     */
    private Integer visIntercomChanNum;
    /**
     *资源权限集  是
     */
    @Column(name = "resAuths", length = 50)
    private String resAuths;

}
