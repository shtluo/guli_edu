package cn.guliedu.eduservice.service.impl;

import cn.guliedu.eduservice.entity.EduCourse;
import cn.guliedu.eduservice.entity.EduCourseDescription;
import cn.guliedu.eduservice.entity.EduTeacher;
import cn.guliedu.eduservice.entity.vo.CourseBaseInfo;
import cn.guliedu.eduservice.entity.vo.CourseFormInfo;
import cn.guliedu.eduservice.entity.vo.CoursePublishVo;
import cn.guliedu.eduservice.entity.vo.QueryCourse;
import cn.guliedu.eduservice.handler.EduException;
import cn.guliedu.eduservice.mapper.EduCourseMapper;
import cn.guliedu.eduservice.service.EduChapterService;
import cn.guliedu.eduservice.service.EduCourseDescriptionService;
import cn.guliedu.eduservice.service.EduCourseService;
import cn.guliedu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-10-29
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //描述service注入进来
    @Autowired
    private EduCourseDescriptionService descriptionService;

    //注入小节
    @Autowired
    private EduVideoService videoService;

    //注入章节
    @Autowired
    private EduChapterService chapterService;

    //添加课程基本信息
    @Override
    public String addInfoCourse(CourseFormInfo courseFormInfo) {
        //1 向课程表添加数据
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseFormInfo,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert == 0) {//失败
            throw new EduException(20001,"添加课程信息失败");
        }
        //课程添加成功之后，获取课程id值
        String id = eduCourse.getId();

        //2 向课程描述表添加数据
        EduCourseDescription description = new EduCourseDescription();
        //设置课程id，因为一对一关系
        description.setId(id);
        description.setDescription(courseFormInfo.getDescription());
        descriptionService.save(description);

        return id;
    }

    //根据课程id查询课程信息
    @Override
    public CourseFormInfo getInfoCourse(String courseId) {
        //1 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseFormInfo courseFormInfo = new CourseFormInfo();
        BeanUtils.copyProperties(eduCourse,courseFormInfo);

        //2 查询描述表
        EduCourseDescription eduCourseDescription = descriptionService.getById(courseId);
        if(eduCourseDescription != null) {
            courseFormInfo.setDescription(eduCourseDescription.getDescription());
        }
        return courseFormInfo;
    }

    //课程修改接口
    @Override
    public void updateCourse(CourseFormInfo courseFormInfo) {
        //修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseFormInfo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update == 0) {
            throw new EduException(20001,"修改失败");
        }
        //修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseFormInfo.getId());
        description.setDescription(courseFormInfo.getDescription());
        descriptionService.updateById(description);
    }

    //获取课程确认信息
    @Override
    public CoursePublishVo getCourseInfoConfirm(String courseId) {
        CoursePublishVo courseConfirmInfo = baseMapper.getCourseConfirmInfo(courseId);
        return courseConfirmInfo;
    }

    //课程条件查询带分类
    @Override
    public void getPageListCourse(Page<EduCourse> pageCourse, QueryCourse queryCourse) {
        //获取条件值
        String title = queryCourse.getTitle();
        String status = queryCourse.getStatus();
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值
        if(!StringUtils.isEmpty(title)) {
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)) {
            wrapper.eq("status",status);
        }
        baseMapper.selectPage(pageCourse,wrapper);
    }

    //删除课程的方法
    @Override
    public void removeCourse(String courseId) {
        //1 根据课程id删除小节
        // TODO 后面完善，删除小节删除视频
        videoService.removeVideoByCourseId(courseId);

        //2 根据课程id删除章节
        chapterService.removeChapterByCourseId(courseId);

        //3 根据课程id删除描述
        descriptionService.removeById(courseId);

        //4 根据课程id删除课程
        int delete = baseMapper.deleteById(courseId);
        if(delete == 0) {
            throw new EduException(20001,"删除课程失败");
        }
    }

    //讲师所讲的课程数据
    @Override
    public List<EduCourse> getCourseInfoByTeacherId(String id) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse> eduCourses = baseMapper.selectList(wrapper);
        return eduCourses;
    }

    //课程分页查询的方法
    @Override
    public Map<String, Object> getPageFrontCourse(Page<EduCourse> pageCourse) {
        baseMapper.selectPage(pageCourse,null);
        long total = pageCourse.getTotal();
        List<EduCourse> records = pageCourse.getRecords();
        long size = pageCourse.getSize();//每页显示记录数
        long pages = pageCourse.getPages();
        long current = pageCourse.getCurrent();//当前页
        boolean hasPrevious = pageCourse.hasPrevious();
        boolean hasNext = pageCourse.hasNext();

        Map<String,Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }

    //1 根课程id查询基本信息
    @Override
    public CourseBaseInfo getBaseInfoCourse(String courseId) {
        return baseMapper.getBaseInfoCourse(courseId);
    }

}
