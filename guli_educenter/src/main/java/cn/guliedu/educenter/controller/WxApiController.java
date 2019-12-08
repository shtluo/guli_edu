package cn.guliedu.educenter.controller;

import cn.guliedu.educenter.entity.UcenterMember;
import cn.guliedu.educenter.service.UcenterMemberService;
import cn.guliedu.educenter.utils.ConstantPropertiesUtil;
import cn.guliedu.educenter.utils.HttpClientUtils;
import cn.guliedu.educenter.utils.JwtUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.HashMap;

@Controller  //不要添加RestController
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    //2 扫描之后调用的方法
    @GetMapping("callback")
    public String callback(String code, String state) {
        System.out.println(code);
        System.out.println(state);
        //1 获取返回临时票据 code
        //2 拿着临时票据请求固定地址
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        //设置参数
        baseAccessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
                code
                );
        //使用httpclient请求拼接好的地址
        try {
            String resultAccessToken = HttpClientUtils.get(baseAccessTokenUrl);
            //获取两个值 access_token 和 openid
            //gson获取json字符串里面两个值
            Gson gson = new Gson();
            //把json字符串变成map集合
            HashMap resultTokenMap = gson.fromJson(resultAccessToken, HashMap.class);
            //从map集合获取数据
            String access_token = (String)resultTokenMap.get("access_token");
            String openid = (String)resultTokenMap.get("openid");

            //3 拿着access_token和openid再去请求地址，得到扫描人信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            baseUserInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
            //使用httpclient请求
            String userInfoResult = HttpClientUtils.get(baseUserInfoUrl);
            //从json字符串获取扫描人信息
            HashMap userInfoMap = gson.fromJson(userInfoResult, HashMap.class);
            String nickname = (String)userInfoMap.get("nickname");//微信昵称
            String headimgurl = (String)userInfoMap.get("headimgurl");//微信头像

            //获取扫描人信息添加数据库
            //根据openid进行判断是否有重复微信用户
            UcenterMember member = memberService.getUserInfoByOpenid(openid);
            if(member == null) {//添加
                member = new UcenterMember();
                member.setOpenid(openid);
                member.setNickname(nickname);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }
            //生成自包含令牌token，把token字符串传递首页面中
            //根据member对象生成jwt的token字符串
            String token = JwtUtils.geneJsonWebToken(member);
            return "redirect:http://localhost:3000?token="+token;
        }catch(Exception e) {
            return null;
        }
    }

    //1 生成扫描二维码
    //请求微信提供固定地址，生成二维码
    @GetMapping("login")
    public String login() {
//        String url = "https://open.weixin.qq.com/connect/qrconnect";
//        url = url+"?appid="+ ConstantPropertiesUtil.WX_OPEN_APP_ID+"&redirect_uri="+ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL+"&response_type=code&scope=SCOPE&state=STATE";
        //1 定义请求固定的地址，参数%s代表占位符
        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //2 对重定向地址进行urlEncode编码
        String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL;
        try {
            redirectUrl = URLEncoder.encode(redirectUrl,"utf-8");
        }catch(Exception e) {
        }
        String state = "atonlineedu";
        //3 把参数传递到占位符位置
        baseUrl = String.format(
                baseUrl,
                ConstantPropertiesUtil.WX_OPEN_APP_ID,
                redirectUrl,
                state
                );
        //4 重定向到地址里面
        return "redirect:"+baseUrl; //重定向到地址
    }


}
