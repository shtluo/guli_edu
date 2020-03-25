package cn.guliedu.eduservice.controller;


import cn.guliedu.common.R;
import cn.guliedu.common.result.CommResult;
import cn.guliedu.eduservice.entity.EduTeacher;
import cn.guliedu.eduservice.entity.vo.QueryTeacher;
import cn.guliedu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-10-22
 */
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
@Api(description = "TE 讲师controller")
public class EduTeacherController {

    @Autowired
    private EduTeacherService teacherService;

    @GetMapping("login")
    public R login() {
        //返回  {"code":20000,"data":{"token":"admin"}}
        return R.ok().data("token", "admin");
    }

    @GetMapping("info")
    public R info() {
        //{"code":20000,"data":{"roles":["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @GetMapping
    public R getAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    @DeleteMapping("{id}")
    public R deleteTeacherId(@PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @GetMapping("getPageTeacher/{page}/{limit}")
    public R getTeacherListPage(@PathVariable long page,
                                @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        //封装分页所有数据到pageTeacher对象里面
        teacherService.page(pageTeacher, null);

        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "TE01 我的文件-分享链接 状态：已完成",notes = "返回分享ID，可根据分享ID查询分享链接")
    @PostMapping("getTeacherPageCondition/{pageNum}/{pageSize}")
    public CommResult getTeacherPageCondition(@ApiParam(value = "当前页") @PathVariable long pageNum,
                                              @ApiParam(value = "步长") @PathVariable long pageSize,
                                              @RequestBody(required = false) QueryTeacher queryTeacher) {
        Page<EduTeacher> pageTeacher = new Page<>(pageNum, pageSize);
        return CommResult.ok(teacherService.getConditonTeacherList(pageTeacher, queryTeacher));
    }

    //5 添加讲师
    @PostMapping("addTeacher")
    public R saveTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //6 根据讲师id查询
    @GetMapping("{id}")
    public R getTeacherId(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    //7 修改讲师的方法
    @PostMapping("updateTeacher")
    public R updateTeacherInfo(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

