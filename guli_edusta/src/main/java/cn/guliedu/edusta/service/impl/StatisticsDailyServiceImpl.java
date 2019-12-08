package cn.guliedu.edusta.service.impl;

import cn.guliedu.common.R;
import cn.guliedu.edusta.client.UcenterClient;
import cn.guliedu.edusta.entity.StatisticsDaily;
import cn.guliedu.edusta.mapper.StatisticsDailyMapper;
import cn.guliedu.edusta.service.StatisticsDailyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-11-05
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    //生成统计数据的方法
    @Override
    public void createStaData(String day) {
        R r = ucenterClient.getRegisterNum(day);
        Integer registerNum = (Integer)r.getData().get("registerNum");

        //把表相同日期的数据删除
        //根据日期进行删除
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);

        //添加数据库里面
        StatisticsDaily daily = new StatisticsDaily();
        daily.setDateCalculated(day);//统计日期
        daily.setRegisterNum(registerNum);//注册人数

        //还有一些数据使用随机数生成
        daily.setCourseNum(RandomUtils.nextInt(100, 200));
        daily.setLoginNum(RandomUtils.nextInt(100, 200));
        daily.setVideoViewNum(RandomUtils.nextInt(100, 200));

        baseMapper.insert(daily);
    }

    //返回图表显示数据
    @Override
    public Map<String, Object> getStaData(String type, String begin, String end) {
        //1 查询满足条件的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.select("date_calculated",type);//查询指定列值
        wrapper.between("date_calculated",begin,end);
        List<StatisticsDaily> statisticsDailyList = baseMapper.selectList(wrapper);

        //2 进行数据封装
        // json数组 ['2019-01-19', '2019-01-20', '2019-01-30']
        // json数组 [6, 10, 20]
        //创建两个list集合
        List<String> dateCalculatedList = new ArrayList<>(); //用于封装日期数据
        List<Integer> staDataList = new ArrayList<>();//用于封装日期对应数据值

        //遍历查询数据，把数据分别放到两个list集合
        for (int i = 0; i < statisticsDailyList.size(); i++) {
            StatisticsDaily daily = statisticsDailyList.get(i);
            //封装日期数据
            String dateCalculated = daily.getDateCalculated();
            //放到list集合
            dateCalculatedList.add(dateCalculated);

            //封装日期对应数据值
            //判断查询统计因子
            switch (type) {
                case "login_num":
                    Integer loginNum = daily.getLoginNum();
                    staDataList.add(loginNum);
                    break;
                case "register_num":
                    Integer registerNum = daily.getRegisterNum();
                    staDataList.add(registerNum);
                    break;
                case "video_view_num":
                    Integer videoViewNum = daily.getVideoViewNum();
                    staDataList.add(videoViewNum);
                    break;
                case "course_num":
                    Integer courseNum = daily.getCourseNum();
                    staDataList.add(courseNum);
                    break;
                default:
                    break;
            }
        }

        //两个封装之后list集合放到map里面
        Map<String,Object> map = new HashMap<>();
        map.put("dateCalculatedList",dateCalculatedList);
        map.put("staDataList",staDataList);

        return map;
    }
}
