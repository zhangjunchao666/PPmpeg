package com.hk.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 胡冉
 * @ClassName FfmpegProperties
 * @Date 2019/5/8 18:45
 * @Version 2.0
 */
@Component
@Data
@ConfigurationProperties(prefix = "ffmpeg")
public class  FfmpegProperties {
    FfmpegUrlProperties url = new FfmpegUrlProperties();
    FfmpegCommandProperties command = new FfmpegCommandProperties();


}
