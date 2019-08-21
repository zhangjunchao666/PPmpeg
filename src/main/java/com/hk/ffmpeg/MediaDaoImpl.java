package com.hk.ffmpeg;

import com.hk.domain.MedioInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 胡冉
 * @date 2018/11/1410:12
 */
public class MediaDaoImpl implements MediaDao {
    /**
     * 视频转码
     *
     * @param ffmpegPath   转码工具的存放路径
     * @param upFilePath   用于指定要转换格式的文件,要截图的视频源文件
     * @param codcFilePath 格式转换后的的文件保存路径
     * @param mediaPicPath 截图保存路径
     * @return
     * @throws Exception
     */
    @Override
    public boolean executeCodecs(String ffmpegPath, String upFilePath, String codcFilePath, String mediaPicPath) throws Exception {
        // 创建一个List集合来保存转换视频文件为mp4格式的命令
        //ffmpeg  -i "http://xxxxxx/video/movie.m3u8" -vcodec copy -acodec copy -absf aac_adtstoasc  output.mp4
        List<String> convert = new ArrayList<String>();
        ffmpegPath = "C:\\ffmpeg.exe";
        // 添加转换工具路径
        convert.add(ffmpegPath);
        // 添加参数＂-i＂，该参数指定要转换的文件
        convert.add("-i");
        // 添加要转换格式的视频文件的路径
        convert.add("http://10.141.6.187:6713/mag/hls/23bcd700637146d1a9c3bbc1d32797a1/0/live.m3u8");
        //指定转换的质量
        convert.add("-qscale");

        convert.add("6");
        //设置音频码率
        convert.add("-ab");

        convert.add("64");
//设置声道数
        convert.add("-ac");

        convert.add("2");
//设置声音的采样频率
        convert.add("-ar");

        convert.add("22050");
//设置帧频
        convert.add("-r");

        convert.add("24");
// 添加参数＂-y＂，该参数指定将覆盖已存在的文件
        convert.add("-y");

        convert.add("E:\\aaa.mp4");

        boolean mark = true;

        ProcessBuilder builder = new ProcessBuilder();

        try {

            builder.command(convert);

            builder.redirectErrorStream(true);

            builder.start();

//  builder.command(cutpic);

            builder.redirectErrorStream(true);

// 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，

//因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易

            builder.start();

        } catch (Exception e) {

            mark = false;

            System.out.println(e);

            e.printStackTrace();

        }

        return mark;

    }

    @Override
    public boolean saveMedia(MedioInfo media) throws Exception {
        return false;
    }

    @Override
    public int getAllMediaCount() throws Exception {
        return 0;
    }

    @Override
    public List<MedioInfo> queryALlMedia(int firstResult, int maxResult) throws Exception {
        return null;
    }

    @Override
    public MedioInfo queryMediaById(int id) throws Exception {
        return null;
    }
}
