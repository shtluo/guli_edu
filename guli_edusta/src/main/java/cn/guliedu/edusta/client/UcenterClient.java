package cn.guliedu.edusta.client;

import cn.guliedu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("guli-educenter")
@Component
public interface UcenterClient {

    //查询某一天注册人数
    @GetMapping("/educenter/member/getRegisterNum/{day}")
    public R getRegisterNum(@PathVariable("day") String day);
}
