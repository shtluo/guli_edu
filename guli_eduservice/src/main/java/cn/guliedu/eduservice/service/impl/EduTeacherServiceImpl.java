package cn.guliedu.eduservice.service.impl;

import cn.guliedu.eduservice.entity.EduTeacher;
import cn.guliedu.eduservice.entity.vo.QueryTeacher;
import cn.guliedu.eduservice.mapper.EduTeacherMapper;
import cn.guliedu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-10-22
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public IPage getConditonTeacherList(Page<EduTeacher> pageTeacher, QueryTeacher queryTeacher) {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        //获取条件值获取出来
        String name = queryTeacher.getName();
        Integer level = queryTeacher.getLevel();
        String begin = queryTeacher.getBegin();
        String end = queryTeacher.getEnd();

        //判断条件值是否为空
        if(!StringUtils.isEmpty(name)) {
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create",end);
        }
        return baseMapper.selectPage(pageTeacher,queryWrapper);
    }
}
