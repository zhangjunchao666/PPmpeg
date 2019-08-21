package com.hk.controller;


import com.hk.common.ObjectMaping;
import com.hk.domain.CameraInfo;
import com.hk.properties.FfmpegProperties;
import com.hk.repository.CameraInfoRepository;
import com.hk.service.FfmpegCommendService;
import com.hk.task.InputStreamProcessTask;
import com.hk.utils.ApiJsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author 胡冉
 * @ClassName 漯周界视频
 * @Date 2019/5/8 11:18
 * @Version 2.0
 */
@Slf4j
@RestController
@RequestMapping(value = "/video/lzj")
public class LZJController {

    @Autowired
    private CameraInfoRepository cameraInfoRepository;
    @Autowired
    FfmpegProperties ffmpegProperties = new FfmpegProperties();
    /**
     * 开始播放标识
     */
    private static final String VAR_FLAG_1 = "1";
    /**
     * 结束播放标识
     */
    private static final String VAR_FLAG_0 = "0";

    private static ExecutorService executorService = new ThreadPoolExecutor(5, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>());
    CompletionService completionService = new ExecutorCompletionService(executorService);
    @Autowired
    private FfmpegCommendService ffmpegCommendService;

    @GetMapping(value = "setSwitchCommend")
    public ApiJsonResult setSwitchCommend(@RequestParam(value = "id", required = false) String id, @RequestParam(value = "flag") String flag
            , @RequestParam(value = "userId") String userId, HttpServletRequest request) throws IOException, InterruptedException {
        ApiJsonResult apiJsonResult = null;
        log.debug("开始播放开始播放开始播放开始播放开始播放开始播放");
       CameraInfo cameraInfo= cameraInfoRepository.getOne(id);
       if(null==cameraInfo){
           return  new ApiJsonResult("200","数据为空");
       }
      String  videoAddress=String.format("%s%s%s%s%s%s","rtsp://admin:",cameraInfo.getPassword(),"@",cameraInfo.getIp(),":","554/mpeg4/ch1/main/av_stream");
       log.info("================>地址为:"+videoAddress);
        //开始播放
        if (VAR_FLAG_1.equals(flag)) {
            this.closeCommend(userId);
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    startVedioBroadCast(id, videoAddress, userId);
                }
            });
            String url = String.format("/hls/%s.m3u8",id);
            apiJsonResult = new ApiJsonResult("200", "开始播放...", url);
        }
        //用户点击关闭  关闭后进程
        if (VAR_FLAG_0.equals(flag)) {
            this.closeCommend(userId);
            apiJsonResult = new ApiJsonResult("200", "关闭...");
        }
        return apiJsonResult;
    }

    public void closeCommend(String key) {
        if (ObjectMaping.mapVeidoIdAndProcess.size() > 0) {

            ObjectMaping.mapVeidoIdAndProcess.entrySet().stream().forEach(x->{
                log.info("关闭进程:"+x.getKey()+":"+x.getValue().toString());
                try {
                    x.getValue().destroy();
                    x.getValue().getInputStream().close();
                    x.getValue().getErrorStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            if (ObjectMaping.mapVeidoIdAndProcess.containsKey(key)) {
                Process process = ObjectMaping.mapVeidoIdAndProcess.get(key);
                if (null != process) {
                    log.info("关闭进程:"+process.toString());

                    try {
                        //强制关闭子进程
                        process.destroy();
                        process.getInputStream().close();
                        process.getErrorStream().close();
                        ObjectMaping.mapVeidoIdAndProcess.remove(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                log.info("该ID不存在......");
            }

        }
    }

    /**
     * 开始转码
     */
    public void startVedioBroadCast(String id, String inputUrl, String key) {
        List<String> ffmpegCommend = ffmpegCommendService.ffmpegCommned(id, inputUrl);
        System.out.println("ffmpegCommend:" + ffmpegCommend.toString());
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(ffmpegCommend);
        log.info("目录============="+ffmpegProperties.getUrl().getDirectory());
        builder.directory(new File(ffmpegProperties.getUrl().getDirectory()));
        Process process = null;
        try {
            //启动进程
            process = builder.start();
            ObjectMaping.mapVeidoIdAndProcess.put(key, process);
            //启动两个线程，一个线程负责读标准输出流，另一个负责读标准错误流
            InputStream inputStream = process.getInputStream();
            InputStream inputStreamError = process.getErrorStream();
            completionService.submit(new InputStreamProcessTask(inputStream));
            completionService.submit(new InputStreamProcessTask(inputStreamError));
            if (0 == process.waitFor()) {
                System.out.println("程序正常退出......");
                process.destroy();
            } else {
                System.out.println("=========================程序异常......");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                process.getErrorStream().close();
                process.getInputStream().close();
                process.getOutputStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



}








































































