package cn.guliedu.eduvod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    //上传视频到阿里云视频点播的方法
    String uploadVideoAliyun(MultipartFile file);

    //删除阿里云视频功能
    void removeVideoAliyun(String videoId);

    //删除多个视频的方法
    void deleteMoreVideo(List<String> videoIds);

    //根据视频id获取视频播放凭证
    String getPlayAuth(String videoId);
}
