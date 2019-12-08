package cn.guliedu.eduservice.service;

import cn.guliedu.eduservice.entity.EduTeacher;
import cn.guliedu.eduservice.entity.vo.QueryTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-10-22
 */
public interface EduTeacherService extends IService<EduTeacher> {

    void getConditonTeacherList(Page<EduTeacher> pageTeacher, QueryTeacher queryTeacher);

    //讲师分页查询的方法
    Map<String,Object> getPageFrontTeacher(Page<EduTeacher> pageTeacher);
}
