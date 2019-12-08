package cn.guliedu.eduvod.controller;

import cn.guliedu.common.R;
import cn.guliedu.eduvod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/vid")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    //根据视频id获取视频播放凭证
    @GetMapping("getVideoPlayAuth/{videoId}")
    public R getVideoPlayAuth(@PathVariable String videoId) {
        String playAuth = vodService.getPlayAuth(videoId);
        return R.ok().data("playAuth",playAuth);
    }

    //上传视频到阿里云视频点播的方法
    @PostMapping("uploadVideoAli")
    public R uploadAliyunVideo(MultipartFile file) {
        //上传过程，返回上传之后视频id
        String videoId = vodService.uploadVideoAliyun(file);
        return R.ok().data("videoId",videoId);
    }

    //删除阿里云视频功能
    @DeleteMapping("{videoId}")
    public R deleteVideoAliyun(@PathVariable String videoId) {
        vodService.removeVideoAliyun(videoId);
        return R.ok();
    }

    //删除多个视频的方法
    @DeleteMapping("removeMoreVideo")
    public R removeMoreVideo(@RequestParam(value = "videoIdList") List videoIdList) {
        vodService.deleteMoreVideo(videoIdList);
        return R.ok();
    }
}
