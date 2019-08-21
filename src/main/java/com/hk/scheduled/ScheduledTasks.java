package com.hk.scheduled;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hikvision.cms.api.common.util.Digests;
import com.hikvision.cms.api.common.util.HttpClientSSLUtils;
import com.hk.domain.CamerasEx;
import com.hk.domain.Encoders;
import com.hk.dto.JsonCamerasExDTO;
import com.hk.dto.JsonEncodersDTO;
import com.hk.service.CamerasExService;
import com.hk.service.EncodersService;
import com.hk.service.MedioInfoService;
import com.hk.task.SaveMedioTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 胡冉
 * @Description: TODO
 * @date 2018/11/1220:59
 */
@Component
@Configurable
@EnableScheduling
@Slf4j
public class ScheduledTasks {
    @Autowired
    private EncodersService encodersService;
    @Autowired
    private CamerasExService camerasExService;
    @Value("${hk.httpUrl}")
    private static String url;
    @Value("${hk.appkey}")
    private static String appkey;
    @Value("${hk.secret}")
    private static String secret;
    @Value("${hk.opUserUuid}")
    private static String opUserUuid;


    /**
     * 每月一号 十点15分触发
     */
    @Scheduled(cron = "0 15 10 1 * ?")
    public void scheduledgetCamerasEx() throws Exception {
        getCamerasEx();
    }

    /**
     * 每月一号 十点10分触发
     */
    @Scheduled(cron = "0 10 10 1 * ?")
    public void scheduledSaveEncodersService() throws Exception {
        saveEncodersService();
    }

    /**
     * 每月一号 十点20分触发  保存数据
     */
    @Scheduled(cron = "0 20 10 1 * ?")
    public void scheduledSaveMedioInfo() throws Exception {
        saveMedioInfo();
    }

    /**
     * 查询监控点
     */

    public static void getCamerasEx() throws Exception {
        String path = url + "/openapi/service/vss/res/getCamerasEx";
        Map<String, Object> map = new HashMap<String, Object>();
        //设置APPKEY
        map.put("appkey", appkey);
        //设置时间参数
        map.put("time", System.currentTimeMillis());
        map.put("pageNo", 1);
        map.put("pageSize", 20);
        map.put("opUserUuid", opUserUuid);
        String params = JSON.toJSONString(map);
        log.info("====================>>>GetDefaultUserUUID_Https 请求参数：[{}]", params);
        String data = HttpClientSSLUtils.doPost(path + "?token=" + Digests.buildToken(path + "?" + params, null, secret), params);
        //System.out.println("================>>>" + JSON.parseObject(data).get("data"));
        //获取总数
        int total = JSONObject.parseObject(JSON.parseObject(data).get("data").toString(), JsonCamerasExDTO.class).getTotal();
        map = Maps.newHashMap();
        map.put("time", System.currentTimeMillis());
        map.put("pageNo", 1);
        map.put("pageSize", total);
        map.put("opUserUuid", opUserUuid);
        JSON.parseObject(data).get("data");
        List<CamerasEx> encoders = JSONObject.parseObject(JSON.parseObject(data).get("data").toString(), JsonCamerasExDTO.class).getList();
        if (!CollectionUtils.isEmpty(encoders)) {
         //   camerasExService.save(encoders);
        }
        System.out.println(encoders);
    }

    /**
     * 查询设备
     *
     * @throws Exception
     */
    public void saveEncodersService() throws Exception {
        String path = url + "/openapi/service/vss/res/getEncoders";
        Map<String, Object> map = new HashMap<String, Object>();
        //设置APPKEY
        map.put("appkey", appkey);
        //设置时间参数
        map.put("time", System.currentTimeMillis());
        map.put("pageNo", 1);
        map.put("pageSize", 5);
        map.put("opUserUuid", opUserUuid);
        String params = JSON.toJSONString(map);
        log.info("====================>>>GetDefaultUserUUID_Https 请求参数：[{}]", params);
        String data = HttpClientSSLUtils.doPost(path + "?token=" + Digests.buildToken(path + "?" + params, null, secret), params);
        System.out.println("================>>>" + JSON.parseObject(data).get("data"));
        JSON.parseObject(data).get("data");
        //获取总数
        int total = JSONObject.parseObject(JSON.parseObject(data).get("data").toString(), JsonEncodersDTO.class).getTotal();
        map = Maps.newHashMap();
        //设置APPKEY
        map.put("appkey", appkey);
        //设置时间参数
        map.put("time", System.currentTimeMillis());
        map.put("pageNo", 1);
        map.put("pageSize", total);
        map.put("opUserUuid", opUserUuid);
        List<Encoders> encoders = JSONObject.parseObject(JSON.parseObject(data).get("data").toString(), JsonEncodersDTO.class).getList();
        if (!CollectionUtils.isEmpty(encoders)) {
            for (Encoders encoder : encoders) {
                Map map1 = Maps.newHashMap();
                map1.put("encoderName", encoder.getEncoderName());
                List<Encoders> encoders1 = encodersService.list(map1);
                if (CollectionUtils.isEmpty(encoders)) {
                    encodersService.save(encoders);
                }
            }

        }


    }

    private static ExecutorService executorService = Executors.newFixedThreadPool(30);
    /**
     * 根据监控点UUID和网域UUID获取预览参数
     */
    @Autowired
    MedioInfoService medioInfoService;

    /**
     * 保存数据
     *
     * @throws InterruptedException
     */
    public void saveMedioInfo() throws InterruptedException {

        List<CamerasEx> camerasExes = camerasExService.findAll();
        CountDownLatch countDownLatch = new CountDownLatch(camerasExes.size());
        for (CamerasEx camerasEx : camerasExes) {
            executorService.submit(new SaveMedioTask(camerasEx, countDownLatch, medioInfoService));

        }
        countDownLatch.await();
    }

    public static void main(String[] args) {
        try {
            getCamerasEx();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
