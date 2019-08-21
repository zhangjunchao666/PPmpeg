package com.hk.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hikvision.cms.api.common.util.Digests;
import com.hikvision.cms.api.common.util.HttpClientSSLUtils;
import com.hk.domain.CamerasEx;
import com.hk.domain.Encoders;
import com.hk.domain.Equip;
import com.hk.dto.JsonCamerasExDTO;
import com.hk.dto.JsonEncodersDTO;
import com.hk.service.*;
import com.hk.task.SaveMedioTask;
import com.hk.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author 胡冉
 * @Description: 新郑管理处
 * @date 2018/11/1218:34
 */
@Slf4j
@RestController
@RequestMapping(value = "/hk")
public class XZController {


    @Value("${hk.httpUrl}")
    private  String url;
    @Value("${hk.appkey}")
    private  String appkey;
    @Value("${hk.secret}")
    private  String secret;
    @Value("${hk.opUserUuid}")
    private  String opUserUuid;
    @Autowired
    private EncodersService encodersService;
    @Autowired
    CamerasExService camerasExService;
    /**
     * 获取默认用户UUID
     */
    private static final String OPENAPI_DEFAULT_URER = "/openapi/service/base/user/getDefaultUserUuid";
    /**
     * 查询编码设备
     */
    private static final String OPENAPI_ENCODERS = "/openapi/service/vss/res/getEncoders";

    /**
     * HTTPS方式
     * 获取默认用户UUID
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getDefaultUserUUIDHttps", method = RequestMethod.GET)
    @ResponseBody
    private String GetDefaultUserUUID_Https(@RequestParam(name = "encoderName", required = false) String encoderName

            , @RequestParam(name = "pageNo", defaultValue = "0") String pageNo
            , @RequestParam(name = "pageSize", defaultValue = "10") String pageSize) throws Exception {
        String path = url + OPENAPI_DEFAULT_URER;
        Map<String, Object> map = new HashMap<String, Object>();
        //设置APPKEY
        map.put("appkey", appkey);
        //设置时间参数
        map.put("time", System.currentTimeMillis());
        map.put("pageNo", NumberUtils.toInt(pageNo));
        map.put("pageSize", NumberUtils.toInt(pageSize));
        if (StringUtils.isNotEmpty(encoderName)) {
            map.put("encoderName", encoderName);
        }
        String params = JSON.toJSONString(map);

        log.info("====================>>>GetDefaultUserUUID_Https 请求参数：[{}]", params);
        String data = HttpClientSSLUtils.doPost(path + "?token=" + Digests.buildToken(path + "?" + params, null, secret), params);
        System.out.println(" ====== testGetDefaultUserUUID 请求返回结果：【{" + data + "}】");
     /*   new Thread(() -> {
            if (null != data) {
                JSONObject jsonObject = new JSONObject();

                List<Encoders> encodersList = JSON.parseArray(jsonObject.get("data").toString(), Encoders.class);
                if (!CollectionUtils.isEmpty(encodersList)) {
                    encodersService.save(encodersList);
                }
            }
        }).start();*/

        return data;
    }

    /**
     * HTTPS方式
     * 查询编码设备
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getEncoders", method = RequestMethod.GET)
    @ResponseBody
    private String getEncoders() throws Exception {
        String path = url + OPENAPI_ENCODERS;
        Map<String, Object> map = new HashMap<String, Object>();
        //设置APPKEY
        map.put("appkey", appkey);
        //设置时间参数
        map.put("time", System.currentTimeMillis());
        String params = JSON.toJSONString(map);
        log.info("====================>>>GetDefaultUserUUID_Https 请求参数：[{}]", params);
        String data = HttpClientSSLUtils.doPost(path + "?token=" + Digests.buildToken(path + "?" + params, null, secret), params);
        System.out.println(" ====== testGetDefaultUserUUID 请求返回结果：【{" + data + "}】");
        return data;
    }

    @Autowired
    private MedioInfoService medioInfoService;
    private ExecutorService executorService= Executors.newFixedThreadPool(30);
    /**
     * 保存视频地址
     */
    @RequestMapping(value = "/saveMedisAddress", method = RequestMethod.GET)
    @ResponseBody
    public  void saveMedisAddress() throws InterruptedException {
     List<CamerasEx> camerasExes=camerasExService.findAll();
        CountDownLatch countDownLatch=new CountDownLatch(camerasExes.size());
        for (CamerasEx camerasEx : camerasExes) {
            executorService.submit(new SaveMedioTask(camerasEx,countDownLatch,medioInfoService));
        }
        countDownLatch.await();
    }

    @RequestMapping(value = "/getCamerasEx", method = RequestMethod.GET)
    public  void getCamerasEx() throws Exception {
        String path = url + "/openapi/service/vss/res/getCamerasEx";
        Map<String, Object> map = new HashMap<String, Object>();
        //设置APPKEY
        map.put("appkey", appkey);
        //设置时间参数
        map.put("time", System.currentTimeMillis());
        map.put("pageNo", 1);
        map.put("pageSize", 906);
        map.put("opUserUuid", opUserUuid);
        String params = JSON.toJSONString(map);
        log.info("====================>>>GetDefaultUserUUID_Https 请求参数：[{}]", params);
        String data = HttpClientSSLUtils.doPost(path + "?token=" + Digests.buildToken(path + "?" + params, null, secret), params);
        //System.out.println("================>>>" + JSON.parseObject(data).get("data"));
        //获取总数
        int total = JSONObject.parseObject(JSON.parseObject(data).get("data").toString(), JsonCamerasExDTO.class).getTotal();
        System.out.println(total);
        List<CamerasEx> encoders = JSONObject.parseObject(JSON.parseObject(data).get("data").toString(), JsonCamerasExDTO.class).getList();
        if (!CollectionUtils.isEmpty(encoders)) {
               camerasExService.save(encoders);
        }
        System.out.println(encoders.toString());
    }
    /**
     * 查询设备
     *
     * @throws Exception
     */
    @RequestMapping(value = "/saveEncodersService", method = RequestMethod.GET)
    public void saveEncodersService() throws Exception {
        String path = url + "/openapi/service/vss/res/getEncoders";
        Map<String, Object> map = new HashMap<String, Object>();
        //设置APPKEY
        map.put("appkey", appkey);
        //设置时间参数
        map.put("time", System.currentTimeMillis());
        map.put("pageNo", 1);
        map.put("pageSize", 156);
        map.put("opUserUuid", opUserUuid);
        String params = JSON.toJSONString(map);
        log.info("====================>>>GetDefaultUserUUID_Https 请求参数：[{}]", params);
        String data = HttpClientSSLUtils.doPost(path + "?token=" + Digests.buildToken(path + "?" + params, null, secret), params);
        //获取总数
        int total = JSONObject.parseObject(JSON.parseObject(data).get("data").toString(), JsonEncodersDTO.class).getTotal();
        System.out.println("获取总数:"+total);

        List<Encoders> encoders = JSONObject.parseObject(JSON.parseObject(data).get("data").toString(), JsonEncodersDTO.class).getList();
        if (!CollectionUtils.isEmpty(encoders)) {
            for (Encoders encoder : encoders) {
                  encodersService.save(encoder);
            }

        }

    }

    /**
     * 保存摄像机信息
     * @throws InterruptedException
     */
    @RequestMapping(value = "/saveMedioInfo", method = RequestMethod.GET)
    public void saveMedioInfo() throws InterruptedException {

        List<CamerasEx> camerasExes = camerasExService.findAll();
        CountDownLatch countDownLatch = new CountDownLatch(camerasExes.size());
        for (CamerasEx camerasEx : camerasExes) {
            executorService.submit(new SaveMedioTask(camerasEx, countDownLatch, medioInfoService));

        }
        countDownLatch.await();
    }



}
