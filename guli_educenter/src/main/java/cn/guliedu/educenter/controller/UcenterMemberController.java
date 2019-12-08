package cn.guliedu.educenter.controller;


import cn.guliedu.common.R;
import cn.guliedu.educenter.entity.UcenterMember;
import cn.guliedu.educenter.service.UcenterMemberService;
import cn.guliedu.educenter.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-11-05
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;

    //根据token字符串获取用户信息
    @PostMapping("getUserInfo/{token}")
    public R getUserInfo(@PathVariable String token) {
        Claims claims = JwtUtils.checkJWT(token);
        String id = (String)claims.get("id");
        String nickname = (String)claims.get("nickname");
        String avatar = (String)claims.get("avatar");
        UcenterMember member = new UcenterMember();
        member.setId(id);
        member.setNickname(nickname);
        member.setAvatar(avatar);
        return R.ok().data("member",member);
    }
    //查询某一天注册人数
    @GetMapping("getRegisterNum/{day}")
    public R getRegisterNum(@PathVariable String day) {
        Integer num = memberService.countUserRegisterNum(day);
        return R.ok().data("registerNum",num);
    }
}

