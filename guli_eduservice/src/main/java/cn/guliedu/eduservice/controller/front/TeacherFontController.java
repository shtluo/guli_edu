package cn.guliedu.eduservice.controller.front;

import cn.guliedu.common.R;
import cn.guliedu.eduservice.entity.EduCourse;
import cn.guliedu.eduservice.entity.EduTeacher;
import cn.guliedu.eduservice.service.EduCourseService;
import cn.guliedu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//前台讲师接口
@RestController
@RequestMapping("/eduservice/teacherfront")
@CrossOrigin
public class TeacherFontController {

    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    //查询讲师详情信息
    @GetMapping("getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable String id) {
        //讲师信息数据
        EduTeacher teacher = teacherService.getById(id);
        //讲师所讲的课程数据
        List<EduCourse> courseList = courseService.getCourseInfoByTeacherId(id);

        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }
    //讲师分页查询的方法
    @GetMapping("getFrontTeacherList/{page}/{limit}")
    public R getFrontTeacherList(@PathVariable long page,
                                 @PathVariable long limit) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);

        //调用service方法实现分页
        Map<String,Object> map = teacherService.getPageFrontTeacher(pageTeacher);
        return R.ok().data(map);
    }

}
