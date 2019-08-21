package com.hk.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * @author 胡冉
 * @Description: 监控点
 * @date 2018/11/1219:58
 * @copyright {@link www.hndfsj.com}
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "HK_CAMERAS_EX")
public class CamerasEx {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", length = 50)
    private Long id;

    /**
     * 监控点UUID 是
     */
    @Column(name = "cameraUuid", length = 50)
    private String cameraUuid;

    /**
     * 监控点名称是
     */
    @Column(name = "cameraName", length = 50)
    private String cameraName;

    /**
     * 监控点类型  是
     */
    private Integer cameraType;

    /**
     * 通道号 是
     */
    private Integer cameraChannelNum;

    /**
     * 专业智能类型码 是
     */
    private String smartType;

    /**
     * 是否支持智能 是
     */
    private Integer smartSupport;

    /**
     * 在线状态 否
     */
    private Integer onLineStatus;
    /**
     * 键盘矩阵UUID 否
     */
    @Column(name = "keyBoardCode", length = 50)
    private String keyBoardCode;

    /**
     * 排序字段  否
     */
    private Integer orderNum;

    /**
     * 更新时间 是
     */
    private Long updateTime;
    /**
     * 中心UUID 是
     */
    @Column(name = "unitUuid", length = 50)
    private String unitUuid;
    /**
     * 区域UUID 是
     */
    @Column(name = "regionUuid", length = 50)
    private String regionUuid;
    /**
     * 编码设备UUID 是
     */
    @Column(name = "encoderUuid", length = 50)
    private String encoderUuid;
    /**
     * 资源权限集  是
     */
    @Column(name = "resAuths", length = 50)
    private String resAuths;

}
