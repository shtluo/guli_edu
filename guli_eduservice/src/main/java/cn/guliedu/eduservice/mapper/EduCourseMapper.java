package cn.guliedu.eduservice.mapper;

import cn.guliedu.eduservice.entity.EduCourse;
import cn.guliedu.eduservice.entity.vo.CourseBaseInfo;
import cn.guliedu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2019-10-29
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    //根据课程id查询课程确认信息
    public CoursePublishVo getCourseConfirmInfo(String courseId);

    //1 根课程id查询基本信息
    CourseBaseInfo getBaseInfoCourse(String courseId);
}
