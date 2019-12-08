package cn.guliedu.edusta.service;

import cn.guliedu.edusta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-11-05
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    //生成统计数据的方法
    void createStaData(String day);

    //返回图表显示数据
    Map<String, Object> getStaData(String type, String begin, String end);
}
