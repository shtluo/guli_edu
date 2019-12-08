package cn.guliedu.educenter.service.impl;

import cn.guliedu.educenter.entity.UcenterMember;
import cn.guliedu.educenter.mapper.UcenterMemberMapper;
import cn.guliedu.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-11-05
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    //查询某一天注册人数
    @Override
    public Integer countUserRegisterNum(String day) {
        return baseMapper.countRegisterNum(day);
    }

    //根据openid进行判断是否有重复微信用户
    @Override
    public UcenterMember getUserInfoByOpenid(String openid) {
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }
}
