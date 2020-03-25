package cn.guliedu.eduservice.service;

import cn.guliedu.eduservice.entity.EduSubject;
import cn.guliedu.eduservice.entity.dto.OneSubjetDto;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2019-10-28
 */
public interface EduSubjectService extends IService<EduSubject> {

    //使用poi读取excel内容，添加到数据库
    List poiReadExcelSubject(MultipartFile file);

    //返回所有分类
    List<OneSubjetDto> getAllSubject();

    //删除分类的方法
    boolean removeSubject(String id);
}
