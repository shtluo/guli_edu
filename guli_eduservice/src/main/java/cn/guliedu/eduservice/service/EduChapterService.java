package cn.guliedu.eduservice.service;

import cn.guliedu.eduservice.entity.EduChapter;
import cn.guliedu.eduservice.entity.dto.ChapterDto;
import cn.guliedu.eduservice.entity.vo.CourseBaseInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-10-29
 */
public interface EduChapterService extends IService<EduChapter> {

    //课程大纲列表的方法
    List<ChapterDto> getAllChapterVideo(String courseId);

    //章节添加接口
    boolean saveChapter(EduChapter eduChapter);

    //删除章节的方法
    boolean removeChapter(String chapterId);

    //2 根据课程id删除章节
    void removeChapterByCourseId(String courseId);

}
