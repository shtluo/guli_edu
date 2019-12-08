package cn.guliedu.eduservice.controller;


import cn.guliedu.common.R;
import cn.guliedu.eduservice.entity.EduTeacher;
import cn.guliedu.eduservice.entity.vo.QueryTeacher;
import cn.guliedu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@CrossOrigin  //跨域问题
public class EduTeacherController {

    //注入service
    @Autowired
    private EduTeacherService teacherService;

    //login
    @GetMapping("login")
    public R login() {
        //返回  {"code":20000,"data":{"token":"admin"}}
        return R.ok().data("token","admin");
    }

    //info
    @GetMapping("info")
    public R info() {
        //{"code":20000,"data":{"roles":["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    //1 查询所有讲师
    @GetMapping
    public R getAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);
    }

    //2 删除讲师的方法
    @DeleteMapping("{id}")
    public R deleteTeacherId(@PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //3 讲师分页查询
    //page代表当前页  limit代表每页记录数
    @GetMapping("getPageTeacher/{page}/{limit}")
    public R getTeacherListPage(@PathVariable long page,
                                @PathVariable long limit) {
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        //封装分页所有数据到pageTeacher对象里面
        teacherService.page(pageTeacher,null);

        long total = pageTeacher.getTotal(); //总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合

//        Map<String,Object> map = new HashMap<>();
//        map.put("total",total);
//        map.put("rows",records);
//        return R.ok().data(map);

        return R.ok().data("total",total).data("rows",records);
    }

    //4 条件查询带分页方法
    //@RequestBody 获取json格式数据 提交方式需要 post提交
    @PostMapping("getTeacherPageCondition/{page}/{limit}")
    public R getTeacherPageCondition(@PathVariable long page,
                                     @PathVariable long limit,
                                     @RequestBody(required = false) QueryTeacher queryTeacher) {
        //创建Page对象
        Page<EduTeacher> pageTeacher = new Page<>(page,limit);
        //调用service的方法
        teacherService.getConditonTeacherList(pageTeacher,queryTeacher);
        long total = pageTeacher.getTotal(); //总记录数
        List<EduTeacher> records = pageTeacher.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }

    //5 添加讲师
    @PostMapping("addTeacher")
    public R saveTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //6 根据讲师id查询
    @GetMapping("{id}")
    public R getTeacherId(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //7 修改讲师的方法
    @PostMapping("updateTeacher")
    public R updateTeacherInfo(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

