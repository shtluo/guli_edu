package cn.guliedu.edusta.controller;


import cn.guliedu.common.R;
import cn.guliedu.edusta.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2019-11-05
 */
@RestController
@RequestMapping("/edusta/daily")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService dailyService;

    //返回图表显示数据
    ///type统计因子
    @GetMapping("getShowData/{type}/{begin}/{end}")
    public R getShowData(@PathVariable String type,
                         @PathVariable String begin,
                         @PathVariable String end) {
        Map<String,Object> dataMap = dailyService.getStaData(type,begin,end);
        return R.ok().data(dataMap);
    }

    //生成统计数据的方法
    @PostMapping("createData/{day}")
    public R createData(@PathVariable String day) {
        dailyService.createStaData(day);
        return R.ok();
    }
}

