package com.hk.service.impl;

import com.hk.properties.FfmpegProperties;
import com.hk.service.FfmpegCommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 胡冉
 * @ClassName FfmpegCommendServiceImpl
 * @Description: TODO
 * @Date 2019/5/8 11:15
 * @Version 2.0
 */
@Service
public class FfmpegCommendServiceImpl implements FfmpegCommendService {
    @Autowired
    private FfmpegProperties ffmpegProperties;


    /**
     * 组装命令
     *
     * @param id       主键
     * @param inputUrl 视频请求地址
     * @return
     */
    @Override
    public List<String> ffmpegCommned(String id, String inputUrl) {
        List<String> ffmpegCommend = new ArrayList<String>(1);
        ffmpegCommend.add(ffmpegProperties.getUrl().getFfmpegExe());
        ffmpegCommend.add("-i");
        ffmpegCommend.add(inputUrl);
        ffmpegCommend.add("-fflags");
        ffmpegCommend.add("flush_packets");
        ffmpegCommend.add("-max_delay");
        ffmpegCommend.add("5");
        ffmpegCommend.add("-flags");
        ffmpegCommend.add("-global_header");
        ffmpegCommend.add("-hls_time");
        ffmpegCommend.add("10");
        ffmpegCommend.add("-hls_list_size");
        ffmpegCommend.add("3");
        ffmpegCommend.add("-vcodec");
        ffmpegCommend.add("copy");
        ffmpegCommend.add("-y");
        String fileName = String.format("%s.m3u8", id);
        ffmpegCommend.add(ffmpegProperties.getUrl().getMeu8OutDirectory() + fileName);
        return ffmpegCommend;
    }


}
