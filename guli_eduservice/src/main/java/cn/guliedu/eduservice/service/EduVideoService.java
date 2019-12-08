package cn.guliedu.eduservice.service;

import cn.guliedu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-10-29
 */
public interface EduVideoService extends IService<EduVideo> {

    //添加小节的方法
    void saveVideo(EduVideo eduVideo);

    //1 根据课程id删除小节
    // TODO 后面完善，删除小节删除视频
    void removeVideoByCourseId(String courseId);

    //删除小节时候，删除阿里云视频
    void removeVideoById(String id);
}
