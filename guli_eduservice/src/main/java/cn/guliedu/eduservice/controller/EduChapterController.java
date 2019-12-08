package cn.guliedu.eduservice.controller;


import cn.guliedu.common.R;
import cn.guliedu.eduservice.entity.EduChapter;
import cn.guliedu.eduservice.entity.dto.ChapterDto;
import cn.guliedu.eduservice.service.EduChapterService;
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
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    //章节添加接口
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        boolean flag = chapterService.saveChapter(eduChapter);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //删除章节的方法
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = chapterService.removeChapter(chapterId);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
    //根据章节id查询章节信息
    @GetMapping("getChapterInfo/{id}")
    public R getChapterInfo(@PathVariable String id) {
        EduChapter eduChapter = chapterService.getById(id);
        return R.ok().data("eduChapter",eduChapter);
    }

    //修改课程章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    //课程大纲列表的方法
    @GetMapping("getChapterVideoAll/{courseId}")
    public R getChapterVideoAll(@PathVariable String courseId) {
        List<ChapterDto> list = chapterService.getAllChapterVideo(courseId);
        return R.ok().data("list",list);
    }
}

