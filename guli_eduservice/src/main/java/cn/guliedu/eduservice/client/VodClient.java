package cn.guliedu.eduservice.client;

import cn.guliedu.common.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("guli-eduvod") //调用哪个服务，写服务名称
@Component
public interface VodClient {

    //删除阿里云视频功能
    @DeleteMapping(value = "/eduvod/vid/{videoId}")
    public R deleteVideoAliyun(@PathVariable("videoId") String videoId);

    //删除多个视频的方法
    @DeleteMapping(value = "/eduvod/vid/removeMoreVideo")
    public R removeMoreVideo(@RequestParam("videoIdList") List videoIdList);

}
