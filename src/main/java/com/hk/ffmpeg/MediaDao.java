package com.hk.ffmpeg;

import com.hk.domain.MedioInfo;

import java.util.List;

/**
 * @author 胡冉
 * @Description: TODO
 * @date 2018/11/1410:09
 * @copyright {@link www.hndfsj.com}
 */
public interface MediaDao {
    /**
     * 视频转码
     * @param ffmpegPath  转码工具的存放路径
     * @param upFilePath  用于指定要转换格式的文件,要截图的视频源文件
     * @param codcFilePath  格式转换后的的文件保存路径
     * @param mediaPicPath  截图保存路径
     * @return
     * @throws Exception
     */
    public boolean executeCodecs(String ffmpegPath,String upFilePath, String codcFilePath, String mediaPicPath)throws Exception;

    /**
     * 保存文件
     * @param media
     * @return
     * @throws Exception
     */
    public boolean saveMedia(MedioInfo media)throws Exception;

    /**
     * 查询本地库中所有记录的数目
     * @return
     * @throws Exception
     */
    public int getAllMediaCount()throws Exception;

    /**
     *  带分页的查询
     * @param firstResult
     * @param maxResult
     * @return
     * @throws Exception
     */
    public List<MedioInfo> queryALlMedia(int firstResult, int maxResult)throws Exception;

    /**
     * 根据Id查询视频
     * @param id
     * @return
     * @throws Exception
     */
    public MedioInfo queryMediaById(int id)throws Exception;
}
