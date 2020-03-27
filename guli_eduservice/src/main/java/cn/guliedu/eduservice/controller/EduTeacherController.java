package cn.guliedu.eduservice.controller;


import cn.guliedu.common.R;
import cn.guliedu.common.result.CommResult;
import cn.guliedu.eduservice.entity.EduTeacher;
import cn.guliedu.eduservice.entity.vo.QueryTeacher;
import cn.guliedu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
        return R.ok().data("token", "admin");
    }

    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @GetMapping
    public R getAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    @DeleteMapping("{id}")
    public CommResult deleteTeacherId(@PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return CommResult.ok();
        } else {
            return CommResult.error("删除失败!");
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

    @ApiOperation(value = "TE01 讲师列表 状态：已完成")
    @PostMapping("getTeacherPageCondition/{pageNum}/{pageSize}")
    public CommResult<IPage> getTeacherPageCondition(@ApiParam(value = "当前页") @PathVariable long pageNum,
                                                     @ApiParam(value = "步长") @PathVariable long pageSize,
                                                     @RequestBody(required = false) QueryTeacher queryTeacher) {
        return CommResult.ok(teacherService.getConditonTeacherList(pageNum,pageSize, queryTeacher));
    }

    @ApiOperation(value = "TE02 添加讲师 状态：已完成")
    @PostMapping("addTeacher")
    public CommResult saveTeacher(@RequestBody EduTeacher eduTeacher) {
        return teacherService.saveTeacher(eduTeacher);
    }

    @ApiOperation(value = "TE04 讲师修改回显 状态：已完成")
    @GetMapping("{id}")
    public CommResult<EduTeacher> getTeacherId(@ApiParam(value = "讲师id") @PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return CommResult.ok(eduTeacher);
    }

    @ApiOperation(value = "TE03 修改讲师 状态：已完成")
    @PutMapping("updateTeacher")
    public CommResult updateTeacherInfo(@RequestBody EduTeacher eduTeacher) {
        return teacherService.updateTeacherInfo(eduTeacher);
    }
}

