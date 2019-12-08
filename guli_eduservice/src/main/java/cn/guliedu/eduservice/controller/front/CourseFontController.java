package cn.guliedu.eduservice.controller.front;

import cn.guliedu.common.R;
import cn.guliedu.eduservice.entity.EduCourse;
import cn.guliedu.eduservice.entity.EduTeacher;
import cn.guliedu.eduservice.entity.dto.ChapterDto;
import cn.guliedu.eduservice.entity.vo.CourseBaseInfo;
import cn.guliedu.eduservice.service.EduChapterService;
import cn.guliedu.eduservice.service.EduCourseService;
import cn.guliedu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//前台课程接口
@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    //根据课程id查询课程详情信息
    @GetMapping("getCourseInfoById/{courseId}")
    public R getCourseInfoById(@PathVariable String courseId) {
        //1 根课程id查询基本信息
        CourseBaseInfo courseBaseInfo = courseService.getBaseInfoCourse(courseId);

        //2 根据课程id查询课程大纲
        List<ChapterDto> allChapterVideo = chapterService.getAllChapterVideo(courseId);

        return R.ok().data("courseBaseInfo",courseBaseInfo).data("allChapterVideo",allChapterVideo);
    }

    //课程分页查询的方法
    @GetMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page,
                                 @PathVariable long limit) {
        //创建page对象
        Page<EduCourse> pageCourse = new Page<>(page,limit);

        //调用service方法实现分页
        Map<String,Object> map = courseService.getPageFrontCourse(pageCourse);
        return R.ok().data(map);
    }

}
