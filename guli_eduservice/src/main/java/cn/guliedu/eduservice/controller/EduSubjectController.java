package cn.guliedu.eduservice.controller;


import cn.guliedu.common.R;
import cn.guliedu.eduservice.entity.dto.OneSubjetDto;
import cn.guliedu.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-10-28
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //删除分类的方法
    @DeleteMapping("{id}")
    public R deleteSubject(@PathVariable String id) {
        boolean flag = subjectService.removeSubject(id);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    //课程分类列表，返回要求格式数据
    @GetMapping("getSubjectList")
    public R getSubjectList() {
        List<OneSubjetDto> list = subjectService.getAllSubject();
        return R.ok().data("list",list);
    }

    //poi获取上传excel，读取内容添加数据库
    @PostMapping("addSubject")
    public R saveSubjectExcel(MultipartFile file) {
        //1 获取上传过来excel文件
        //2 使用poi读取excel内容，添加到数据库
        List<String> msg =subjectService.poiReadExcelSubject(file);
        if(msg.size()==0) {//成功
            return R.ok();
        } else {//失败
            return R.error().data("msg",msg);
        }
    }
}

