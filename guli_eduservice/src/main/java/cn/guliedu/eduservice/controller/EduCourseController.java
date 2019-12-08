package cn.guliedu.eduservice.controller;


import cn.guliedu.common.R;
import cn.guliedu.eduservice.entity.EduCourse;
import cn.guliedu.eduservice.entity.EduTeacher;
import cn.guliedu.eduservice.entity.vo.CourseFormInfo;
import cn.guliedu.eduservice.entity.vo.CoursePublishVo;
import cn.guliedu.eduservice.entity.vo.QueryCourse;
import cn.guliedu.eduservice.entity.vo.QueryTeacher;
import cn.guliedu.eduservice.service.EduChapterService;
import cn.guliedu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-10-29
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    //删除课程的方法
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }

    //添加课程基本信息
    @PostMapping("addCourseInfo")
    public R saveCourseInfo(@RequestBody CourseFormInfo courseFormInfo) {
        String courseId = courseService.addInfoCourse(courseFormInfo);
        //返回添加之后课程id
        return R.ok().data("courseId",courseId);
    }

    //根据课程id查询课程信息
    @GetMapping("{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseFormInfo courseFormInfo = courseService.getInfoCourse(courseId);
        return R.ok().data("courseInfo",courseFormInfo);
    }

    //课程修改接口
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseFormInfo courseFormInfo) {
        courseService.updateCourse(courseFormInfo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourse/{id}")
    public R getPublishCourse(@PathVariable String id) {
        CoursePublishVo courseInfoConfirm = courseService.getCourseInfoConfirm(id);
        return R.ok().data("courseInfoConfirm",courseInfoConfirm);
    }

    //课程最终发布功能
    @PutMapping("publishCourse/{courseId}")
    public R publishCourse(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);//课程id
        eduCourse.setStatus("Normal");//课程状态 Normal已发布
        courseService.updateById(eduCourse);
        return R.ok();
    }

    //4 课程条件查询带分页方法
    //@RequestBody 获取json格式数据 提交方式需要 post提交
    @PostMapping("getCoursePageCondition/{page}/{limit}")
    public R getCoursePageCondition(@PathVariable long page,
                                     @PathVariable long limit,
                                     @RequestBody(required = false) QueryCourse QueryCourse) {
        //创建Page对象
        Page<EduCourse> pageCourse = new Page<>(page,limit);

        //调用service的方法
        courseService.getPageListCourse(pageCourse,QueryCourse);

        long total = pageCourse.getTotal(); //总记录数
        List<EduCourse> records = pageCourse.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }
}

