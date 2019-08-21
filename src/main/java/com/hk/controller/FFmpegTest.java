package com.hk.controller;

import com.hk.task.InputStreamProcessTask;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author 胡冉
 * @ClassName FFmpegTest
 * @Description: TODO
 * @Date 2019/8/20 14:47
 * @Version 2.0
 */
public class FFmpegTest {
    private static ExecutorService executorService = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS, new
            LinkedBlockingQueue<Runnable>());
    static CompletionService completionService = new ExecutorCompletionService(executorService);

    public static void main(String[] args) throws InterruptedException {
        List<String> ffmpegCommend = ffmpegCommned();
        System.out.println("ffmpegCommend:" + ffmpegCommend.toString());
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(ffmpegCommend);
        Process process = null;
        try {
            //启动进程
            process = builder.start();
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
        Thread.currentThread().join();
    }

    public static List<String> ffmpegCommned() {
        List<String> ffmpegCommend = new ArrayList<String>();
        ffmpegCommend.add("C:\\ffmpeg\\bin\\ffmpeg.exe");
        ffmpegCommend.add("-i");
        ffmpegCommend.add("rtsp://admin:DFSJjszx123@192.168.10.167:554/h264/ch1/main/av_stream");
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
        ffmpegCommend.add("E://hls//aaaa.m3u8");
        return ffmpegCommend;
    }
}
