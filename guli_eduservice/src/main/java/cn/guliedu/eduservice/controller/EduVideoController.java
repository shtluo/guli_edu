package cn.guliedu.eduservice.controller;


import cn.guliedu.common.R;
import cn.guliedu.eduservice.entity.EduVideo;
import cn.guliedu.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-10-29
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;

    //添加小节的方法
    @PostMapping("addVideo")
    public  R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.saveVideo(eduVideo);
        return R.ok();
    }
    //删除小节
    // TODO 删除小节时候，删除阿里云视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        videoService.removeVideoById(id);
        return R.ok();
    }

    //根据id查询小节
    @GetMapping("{id}")
    public R getVideoInfo(@PathVariable String id) {
        EduVideo video = videoService.getById(id);
        return R.ok().data("video",video);
    }

    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.ok();
    }

}

