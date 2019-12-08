package cn.guliedu.eduservice.service;

import cn.guliedu.eduservice.entity.EduCourse;
import cn.guliedu.eduservice.entity.vo.CourseBaseInfo;
import cn.guliedu.eduservice.entity.vo.CourseFormInfo;
import cn.guliedu.eduservice.entity.vo.CoursePublishVo;
import cn.guliedu.eduservice.entity.vo.QueryCourse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-10-29
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程基本信息
    String addInfoCourse(CourseFormInfo courseFormInfo);

    //根据课程id查询课程信息
    CourseFormInfo getInfoCourse(String courseId);

    //课程修改接口
    void updateCourse(CourseFormInfo courseFormInfo);

    //根据课程id查询课程确认信息
    public CoursePublishVo getCourseInfoConfirm(String courseId);

    void getPageListCourse(Page<EduCourse> pageCourse, QueryCourse queryCourse);

    //删除课程的方法
    void removeCourse(String courseId);

    //讲师所讲的课程数据
    List<EduCourse> getCourseInfoByTeacherId(String id);

    //课程分页查询的方法
    Map<String, Object> getPageFrontCourse(Page<EduCourse> pageCourse);

    //1 根课程id查询基本信息
    CourseBaseInfo getBaseInfoCourse(String courseId);
}
