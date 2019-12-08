package cn.guliedu.eduservice.service.impl;

import cn.guliedu.eduservice.entity.EduChapter;
import cn.guliedu.eduservice.entity.EduVideo;
import cn.guliedu.eduservice.entity.dto.ChapterDto;
import cn.guliedu.eduservice.entity.dto.VideoDto;
import cn.guliedu.eduservice.handler.EduException;
import cn.guliedu.eduservice.mapper.EduChapterMapper;
import cn.guliedu.eduservice.service.EduChapterService;
import cn.guliedu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-10-29
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    //课程大纲列表的方法
    @Override
    public List<ChapterDto> getAllChapterVideo(String courseId) {
        //1 查询课程里面所有章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        //2 查询课程里面所有小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        //创建list集合用于最终封装数据
        List<ChapterDto> finalChapterVideoList = new ArrayList<>();

        //3 把所有章节遍历
        for (int i = 0; i < eduChapterList.size(); i++) {
            //得到每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            //eduChapter对象变成ChapterDto对象
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(eduChapter,chapterDto);
            //把chapterDto对象放到list集合
            finalChapterVideoList.add(chapterDto);

            //创建list集合用于封装小节
            List<VideoDto> videoList = new ArrayList<>();

            //4 封装小节时间
            for (int m = 0; m < eduVideoList.size(); m++) {
                //得到每个小节
                EduVideo eduVideo = eduVideoList.get(m);
                //判断章节id和小节chapterid是否一样
                if(eduChapter.getId().equals(eduVideo.getChapterId())) {
                    //eduVideo变成dto对象
                    VideoDto videoDto = new VideoDto();
                    BeanUtils.copyProperties(eduVideo,videoDto);
                    //放到集合
                    videoList.add(videoDto);
                }
            }
            //把小节部分放到每个章节里面
            chapterDto.setChildren(videoList);
        }
        return finalChapterVideoList;
    }

    //章节添加接口
    @Override
    public boolean saveChapter(EduChapter eduChapter) {
        //判断
        EduChapter existChapter = this.existChapter(eduChapter.getTitle(), eduChapter.getCourseId());
        if(existChapter == null) {
            int insert = baseMapper.insert(eduChapter);
            return insert>0;
        } else {
            throw new EduException(20001,"章节已经重复");
        }
    }

    //删除章节的方法
    @Override
    public boolean removeChapter(String chapterId) {
        //判断章节里面是否有小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if(count == 0) {//没有小节，删除
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        } else {//不删除
            throw new EduException(20001,"不能删除");
        }
    }

    //2 根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }

    //判断课程里面是否有相同名称章节
    private EduChapter existChapter(String chapterName,String courseId) {
//        SELECT *
//                FROM edu_chapter
//        WHERE course_id=? AND title =?
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("title",chapterName);
        EduChapter eduChapter = baseMapper.selectOne(wrapper);
        return eduChapter;
    }
}
