package cn.guliedu.eduservice.service.impl;

import cn.guliedu.common.R;
import cn.guliedu.common.enums.YESNOEnum;
import cn.guliedu.common.result.CommResult;
import cn.guliedu.eduservice.entity.EduTeacher;
import cn.guliedu.eduservice.entity.vo.QueryTeacher;
import cn.guliedu.eduservice.mapper.EduTeacherMapper;
import cn.guliedu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

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
    public IPage getConditonTeacherList(Long pageNum, Long pageSize, QueryTeacher queryTeacher) {
        Page<EduTeacher> pageTeacher = new Page<>(pageNum, pageSize);
        QueryWrapper<EduTeacher> queryWrapper = this.getEduTeacherQueryWrapper(queryTeacher);
        return baseMapper.selectPage(pageTeacher, queryWrapper);
    }

    /**
     * 查询条件封装
     *
     * @param queryTeacher
     * @return
     */
    private QueryWrapper<EduTeacher> getEduTeacherQueryWrapper(QueryTeacher queryTeacher) {
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        //获取条件值获取出来
        String name = queryTeacher.getName();
        Integer level = queryTeacher.getLevel();
        String begin = queryTeacher.getBegin();
        String end = queryTeacher.getEnd();

        //判断条件值是否为空
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            queryWrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }
        return queryWrapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommResult updateTeacherInfo(EduTeacher eduTeacher) {
        updateById(eduTeacher);
        return CommResult.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommResult saveTeacher(EduTeacher eduTeacher) {
        eduTeacher.setDeleted(false);
        eduTeacher.setGmtCreate(new Date());
        eduTeacher.setGmtModified(new Date());
        save(eduTeacher);
        return CommResult.ok();
    }
}
