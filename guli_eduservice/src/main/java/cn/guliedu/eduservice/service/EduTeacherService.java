package cn.guliedu.eduservice.service;

import cn.guliedu.eduservice.entity.EduTeacher;
import cn.guliedu.eduservice.entity.vo.QueryTeacher;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-10-22
 */
public interface EduTeacherService extends IService<EduTeacher> {

    IPage getConditonTeacherList(Page<EduTeacher> pageTeacher, QueryTeacher queryTeacher);
}
