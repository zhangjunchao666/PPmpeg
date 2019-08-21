package com.hk.task;

import com.google.common.collect.Maps;
import com.hk.domain.CamerasEx;
import com.hk.domain.MedioInfo;
import com.hk.service.MedioInfoService;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author 胡冉
 * @Description: TODO
 * @date 2018/11/1317:54
 */
public class SaveMedioTask implements Callable<Long> {
    private CamerasEx camerasEx;
    private CountDownLatch countDownLatch;
    private MedioInfoService medioInfoService;

    public SaveMedioTask(CamerasEx camerasEx, CountDownLatch countDownLatch, MedioInfoService medioInfoService) {
        this.camerasEx = camerasEx;
        this.countDownLatch = countDownLatch;
        this.medioInfoService = medioInfoService;
    }

    @Override
    public Long call() throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("http://10.141.6.187:6713")
                .append("/")
                .append("mag")
                .append("/")
                .append("hls")
                .append("/")
                .append(camerasEx.getCameraUuid())
                .append("/")
                .append("0")
                .append("/")
                .append("live.m3u8");
        MedioInfo medioInfo = new MedioInfo();
        medioInfo.setCameraName(camerasEx.getCameraName())
                .setCameraUuid(camerasEx.getCameraUuid()).setAddress(stringBuilder.toString().trim()).setTime(new Timestamp(System.currentTimeMillis()));
        medioInfoService.save(medioInfo);
        countDownLatch.countDown();
        return 0L;
    }

}
