package com.hk;

import com.alibaba.fastjson.JSON;
import com.hikvision.cms.api.common.util.Digests;
import com.hikvision.cms.api.common.util.HttpClientSSLUtils;
import com.hk.domain.CamerasEx;
import com.hk.domain.MedioInfo;
import com.hk.service.MedioInfoService;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author 胡冉
 * @Description: TODO
 * @date 2018/11/1317:05
 */
public class SaveDataTask implements Callable<Long> {
    CamerasEx camerasExes;
    private MedioInfoService previewParamService;
    CountDownLatch countDownLatch;
    SaveDataTask(CamerasEx camerasExes, MedioInfoService previewParamService, CountDownLatch countDownLatch) {
        this.camerasExes = camerasExes;
        this.previewParamService=previewParamService;
        this.countDownLatch=countDownLatch;
    }

    @Override
    public Long call() throws Exception {
        String path = "http://10.141.6.187:80" + "/openapi/service/vss/preview/getPreviewParamByCameraUuid";
        Map<String, Object> map = new HashMap<String, Object>();
        //设置APPKEY
        map.put("appkey","37f40428");
        //设置时间参数
        map.put("time", System.currentTimeMillis());
        map.put("opUserUuid", "5b2eb534696b11e89c2e438f92627767");
        map.put("cameraUuid",camerasExes.getCameraUuid());
        map.put("netZoneUuid", "a456954df0434e398d5e1b5a5a624ae7");
        String params = JSON.toJSONString(map);
        String data = HttpClientSSLUtils.doPost(path + "?token=" + Digests.buildToken(path + "?" + params, null, "71c31d3c9a5e4fd1bce9fb6e9efa0070"), params);

        return 0L;
    }
}
