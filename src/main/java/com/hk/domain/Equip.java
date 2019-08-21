package com.hk.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author 胡冉
 * @Description: TODO
 * @date 2018/11/1910:21
 */
@Data
@Entity
@Accessors(chain = true)
@Table(name = "BAS_EQUIP")
@GenericGenerator(name = "id", strategy = "uuid")
public class Equip {
    /**
     * id
     */@Id
    @GeneratedValue(generator = "id")
    @Column(length = 32)
    private java.lang.String id;
    /**
     * 设备名称
     */
    @Column(name = "equipName")

    private java.lang.String equipName;
    /**
     * 设备编号
     */
    @Column(name = "equipSn")

    private java.lang.String equipSn;
    /**
     * 设备桩号
     */
    @Column(name = "mileage")

    private java.lang.String mileage;
    /**
     * 方向
     */
    @Column(name = "direction")

    private java.lang.String direction;
    /**
     * 厂家
     */
    @Column(name = "brand")

    private java.lang.String brand;
    /**
     * 设备类型
     */
    @Column(name = "equipType")

    private java.lang.String equipType;
    /**
     * 位置
     */
    @Column(name = "position")

    private java.lang.String position;
    /**
     * createTime
     */
    @Column(name = "createTime")

    private java.util.Date createTime;
    /**
     * creator
     */
    @Column(name = "creator")

    private java.lang.String creator;
    /**
     * modifyTime
     */
    @Column(name = "modifyTime")

    private java.util.Date modifyTime;
    /**
     * 监控中心
     */
    @Column(name = "center")

    private java.lang.String center;
    /**
     * 所属道路
     */
    @Column(name = "road")

    private java.lang.String road;
    /**
     * 投入使用时间
     */
    @Column(name = "useTime")

    private java.util.Date useTime;
    /**
     * 情报板类型
     */
    @Column(name = "intelligenceBoardType")

    private java.lang.String intelligenceBoardType;
    /**
     * 情报板规格
     */
    @Column(name = "intelligenceBoardStandard")

    private java.lang.String intelligenceBoardStandard;
    /**
     * 字体点阵
     */
    @Column(name = "fontMatrix")

    private java.lang.String fontMatrix;
    /**
     * 是否显示 0不显示1显示
     */
    @Column(name = "isView")

    private java.lang.Long isView;
    /**
     * 旅行时间  0否1是
     */
    @Column(name = "isLx")

    private java.lang.Long isLx;
    /**
     * 负责人
     */
    @Column(name = "responsePerson")

    private java.lang.String responsePerson;
    /**
     * 设备型号
     */
    @Column(name = "equipVersion")

    private java.lang.String equipVersion;
    /**
     * 限速标志样式
     */
    @Column(name = "limitSpeedStyle")

    private java.lang.String limitSpeedStyle;
    /**
     * 是否云台 0否1是
     */
    @Column(name = "isCloudControl")

    private java.lang.String isCloudControl;
    /**
     * 纬度
     */
    @Column(name = "latitude")

    private java.lang.String latitude;
    /**
     * 经度
     */
    @Column(name = "longitude")
    private java.lang.String longitude;
    /**
     * 是否删除 0否1是
     */
    @Column(name = "isDelete")
    private java.lang.Long isDelete;
    /**
     * 设备状态
     */
    @Column(name = "equipStatus")
    private java.lang.String equipStatus;
    /**
     * 设备连接地址 串口或IP
     */
    @Column(name = "equipConnection")
    private java.lang.String equipConnection;
    /**
     * 通讯方式  TCP UDP 串口
     */
    @Column(name = "equipCommunicateType")
    private java.lang.String equipCommunicateType;
    /**
     * 视频流地址
     */
    @Column(name = "videoAddress")
    private java.lang.String videoAddress;
    /**
     * 情报板类型 1文字情报板 2 图形情报板 3 收费站摄像机 4.道路摄像机
     */
    @Column(name = "type")
    private java.lang.Long type;
    /**
     * 桩号对应的数字 去掉符号
     */
    @Column(name = "nummileage")
    private java.math.BigDecimal nummileage;
    /**
     * 情报板样式
     */
    @Column(name = "equipStyle")
    private java.lang.String equipStyle;
    /**
     * 摄像机所属站
     */
    @Column(name = "underWho")
    private java.lang.String underWho;
}
