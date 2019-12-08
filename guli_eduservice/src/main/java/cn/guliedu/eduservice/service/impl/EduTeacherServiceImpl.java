package cn.guliedu.eduservice.service.impl;

import cn.guliedu.eduservice.entity.EduTeacher;
import cn.guliedu.eduservice.entity.vo.QueryTeacher;
import cn.guliedu.eduservice.handler.EduException;
import cn.guliedu.eduservice.mapper.EduTeacherMapper;
import cn.guliedu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//    @Autowired
//    private EduTeacherMapper eduTeacherMapper;

    //条件查询带分页过程
    @Override
    public void getConditonTeacherList(Page<EduTeacher> pageTeacher, QueryTeacher queryTeacher) {

        //模拟异常
//        try {
//            int i = 10/0;
//        }catch(Exception e) {
//            //手动执行自定义异常
//            throw new EduException(20001,"自定义异常执行了..");
//        }

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
        baseMapper.selectPage(pageTeacher,queryWrapper);
    }

    //讲师分页查询的方法
    @Override
    public Map<String,Object> getPageFrontTeacher(Page<EduTeacher> pageTeacher) {
        baseMapper.selectPage(pageTeacher,null);
        //从pageTeacher获取分页所有数据
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        long size = pageTeacher.getSize();//每页显示记录数
        long pages = pageTeacher.getPages();
        long current = pageTeacher.getCurrent();//当前页
        boolean hasPrevious = pageTeacher.hasPrevious();
        boolean hasNext = pageTeacher.hasNext();
        //把分页所有数据放到map集合，把map集合返回
        Map<String,Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);
        return map;
    }
}
