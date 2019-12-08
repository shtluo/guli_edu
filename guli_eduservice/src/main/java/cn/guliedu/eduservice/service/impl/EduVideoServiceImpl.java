package cn.guliedu.eduservice.service.impl;

import cn.guliedu.common.R;
import cn.guliedu.eduservice.client.VodClient;
import cn.guliedu.eduservice.entity.EduVideo;
import cn.guliedu.eduservice.handler.EduException;
import cn.guliedu.eduservice.mapper.EduVideoMapper;
import cn.guliedu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-10-29
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;
    //添加小节的方法
    @Override
    public void saveVideo(EduVideo eduVideo) {
//        SELECT *
//                FROM edu_video
//        WHERE course_id=? AND chapter_id=? AND title=?
        //1 判断小节是否重复
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",eduVideo.getCourseId());
        wrapper.eq("chapter_id",eduVideo.getChapterId());
        wrapper.eq("title",eduVideo.getTitle());
        EduVideo existEduVideo = baseMapper.selectOne(wrapper);
        //判断
        if(existEduVideo == null) {//没有重复小节
            baseMapper.insert(eduVideo);
        } else {
            throw new EduException(20001,"小节重复");
        }
    }

    //1 根据课程id删除小节
    @Override
    public void removeVideoByCourseId(String courseId) {

        //删除课程视频
        //查询课程所有视频id，根据视频id删除
        QueryWrapper<EduVideo> wrapper1Video = new QueryWrapper<>();
        wrapper1Video.eq("course_id",courseId);
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper1Video);

        List<String> videoIds = new ArrayList<>();
        //获取所有视频id
        for (int i = 0; i < eduVideos.size(); i++) {
            //获取每个小节
            EduVideo eduVideo = eduVideos.get(i);
            //每个小节视频id
            String videoSourceId = eduVideo.getVideoSourceId();
            if(!StringUtils.isEmpty(videoSourceId)) {
                videoIds.add(videoSourceId);
            }
        }
        vodClient.removeMoreVideo(videoIds);

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }

    //删除小节时候，删除阿里云视频
    @Override
    public void removeVideoById(String id) {
        //删除视频
        //根据小节id查询视频id
        EduVideo eduVideo = baseMapper.selectById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //如果小节里面有视频id，进行删除
        if(!StringUtils.isEmpty(videoSourceId)) {
           vodClient.deleteVideoAliyun(videoSourceId);
        }
        baseMapper.deleteById(id);
    }
}
