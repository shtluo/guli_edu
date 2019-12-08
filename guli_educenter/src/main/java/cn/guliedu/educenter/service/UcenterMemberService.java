package cn.guliedu.educenter.service;

import cn.guliedu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-11-05
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //查询某一天注册人数
    public Integer countUserRegisterNum(String day);

    //根据openid进行判断是否有重复微信用户
    UcenterMember getUserInfoByOpenid(String openid);
}
