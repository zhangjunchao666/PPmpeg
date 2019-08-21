
package com.hk.domain;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import javax.persistence.*;

/**
 *  摄像机信息-漯周界
 *
 * @author Hu.Ran<Auto generate>
 * @version 2019-03-18
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
@Entity
@Table(name = "sys_camera_info")
public class CameraInfo   {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /**
     * cameraId
     */    @Column(name = "camera_id")

    private String cameraId;


    /**
     * 设备桩号
     */
    @Column(name = "camera_mileage")
    private String cameraMileage;
    /**
     * 方向
     */
    @Column(name = "direction")
    private String direction;



    /**
     * createTime
     */
    @Column(name = "create_time")
    private java.util.Date createTime;
    /**
     * 所属道路
     */
    @Column(name = "road")
    private String road;

    /**
     * 经度
     */
    @Column(name = "latitude")
    private String latitude;
    /**
     * longitude
     */
    @Column(name = "longitude")
    private String longitude;


    @Column(name = "ip")
    private String ip;
    @Column(name = "password")
    private String password;
    /**
     * 视频流地址
     */
    @Column(name = "video_address")
    private String videoAddress;


}

	
