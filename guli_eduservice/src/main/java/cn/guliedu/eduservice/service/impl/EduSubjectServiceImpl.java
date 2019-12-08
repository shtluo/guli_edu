package cn.guliedu.eduservice.service.impl;

import cn.guliedu.eduservice.entity.EduSubject;
import cn.guliedu.eduservice.entity.dto.OneSubjetDto;
import cn.guliedu.eduservice.entity.dto.TwoSubjetDto;
import cn.guliedu.eduservice.handler.EduException;
import cn.guliedu.eduservice.mapper.EduSubjectMapper;
import cn.guliedu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2019-10-28
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //使用poi读取excel内容，添加到数据库
    @Override
    public List poiReadExcelSubject(MultipartFile file) {
        try {
            //1 创建workbook，传递文件输入流
            InputStream inputStream = file.getInputStream();
            Workbook workbook = new HSSFWorkbook(inputStream);

            //2 获取sheet
            Sheet sheet = workbook.getSheetAt(0);

            //3 获取行，遍历获取到
            //sheet.getRow(0);
            //获取最后一行的索引值
            int lastRowNum = sheet.getLastRowNum();

            //sheet.getPhysicalNumberOfRows()

            //接受错误信息
            List<String> msg = new ArrayList<>();
            //遍历获取
            for (int i=1;i<=lastRowNum;i++) {
                Row row = sheet.getRow(i);
                //4 获取cell，固定的
                //获取第一列
                Cell cellOne = row.getCell(0);
                //判断是否为空
                if(cellOne == null) {
                    //向list设置错误提示
                    msg.add("第"+(i+1)+"行第1列数据为空");
                    //跳出当前循环操作
                    continue;
                }
                //5 获取cell里面内容
                //第一列值是一级分类
                String cellOneValue = cellOne.getStringCellValue();
                //判断
                if(StringUtils.isEmpty(cellOneValue)) {
                    //向list设置错误提示
                    msg.add("第"+(i+1)+"行第1列数据为空");
                    //跳出当前循环操作
                    continue;
                }

                //获取第二列
                Cell cellTwo = row.getCell(1);
                if(cellTwo == null) {
                    //向list设置错误提示
                    msg.add("第"+(i+1)+"行第2列数据为空");
                    //跳出当前循环操作
                    continue;
                }
                //第二列值是二级分类
                String cellTwoValue = cellTwo.getStringCellValue();
                if(StringUtils.isEmpty(cellOneValue)) {
                    //向list设置错误提示
                    msg.add("第"+(i+1)+"行第2列数据为空");
                    //跳出当前循环操作
                    continue;
                }

                //6 获取内容添加数据库，实现二级分类效果
                //添加一级分类
                //判断一级分类在数据库表是否存在
                EduSubject eduSubjectOne = this.existOneSubject(cellOneValue);
                if(eduSubjectOne == null) { //表没有相同的一级分类，实现添加
                    eduSubjectOne = new EduSubject();
                    eduSubjectOne.setTitle(cellOneValue);
                    eduSubjectOne.setParentId("0");
                    baseMapper.insert(eduSubjectOne);
                    //添加一级分类之后，获取一级分类id值
                }
                //添加一级分类之后，获取一级分类id值
                String pid = eduSubjectOne.getId();
                //添加二级分类
                //判断数据库表是否存在相同的二级分类
                EduSubject eduSubjectTwo = this.existTwoSubject(cellTwoValue, pid);
                if(eduSubjectTwo == null) {//添加二级分类
                    eduSubjectTwo = new EduSubject();
                    eduSubjectTwo.setTitle(cellTwoValue);
                    eduSubjectTwo.setParentId(pid);
                    baseMapper.insert(eduSubjectTwo);
                }
            }
            return msg;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //返回所有的分类
    @Override
    public List<OneSubjetDto> getAllSubject() {
        //1 查询所有一级分类
        //parentid=0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //2 查询所有二级分类
        // parentid!=0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建list集合。用于最终数据返回封装
        List<OneSubjetDto> finalSubjectList = new ArrayList<>();
        //3 封装一级分类
        //遍历所有一级分类，把id和title获取出来，放到finalSubjectList
        for (int i = 0; i < oneSubjectList.size(); i++) {
            //获取集合每个一级分类
            EduSubject oneSubject = oneSubjectList.get(i);
            //oneSubject值添加OneSubjetDto里面值
            OneSubjetDto oneSubjetDto = new OneSubjetDto();
            BeanUtils.copyProperties(oneSubject,oneSubjetDto);
            //一级分类dto对象放到最终集合里面
            finalSubjectList.add(oneSubjetDto);

            //创建集合用于封装二级分类
            List<TwoSubjetDto> twoSubjectDtoList = new ArrayList<>();

            //在一级分类循环里面嵌套，遍历所有的二级分类
            for (int m = 0; m < twoSubjectList.size(); m++) {
                //获取每个二级分类
                EduSubject twoSubject = twoSubjectList.get(m);
                //判断 一级分类id 和 二级分类parentid是否一样
                if(oneSubject.getId().equals(twoSubject.getParentId())) {
                    //twoSubject转换 TwoSubjetDto
                    TwoSubjetDto twoSubjetDto = new TwoSubjetDto();
                    BeanUtils.copyProperties(twoSubject,twoSubjetDto);
                    //放到集合
                    twoSubjectDtoList.add(twoSubjetDto);
                }
            }
            //把封装之后children的二级分类集合放到一级分类中
            oneSubjetDto.setChildren(twoSubjectDtoList);
        }
        return finalSubjectList;
    }

    //删除分类的方法
    @Override
    public boolean removeSubject(String id) {
        //判断删除的分类下面是否有子分类
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        //if判断
        if(count == 0) {//没有子分类
            //直接删除
            int rows = baseMapper.deleteById(id);
            return rows>0;
        } else { //有子分类
            //不能删除
            throw new EduException(20001,"不能删除");
        }
    }

    //添加一级分类
    @Override
    public boolean addOneSubject(EduSubject eduSubject) {
        //判断一级分类是否存在
        EduSubject existEduSubject = this.existOneSubject(eduSubject.getTitle());
        if(existEduSubject == null) {
            //添加
            eduSubject.setParentId("0");//一级分类parentid值是0
            int insert = baseMapper.insert(eduSubject);
            return insert>0;
        }
        return false;
    }

    //添加二级分类
    @Override
    public boolean addTwoSubject(EduSubject eduSubject) {
        //判断二级分类是否存在
        EduSubject existEduSubject = this.existTwoSubject(eduSubject.getTitle(), eduSubject.getParentId());
        if(existEduSubject == null) {
            int insert = baseMapper.insert(eduSubject);
            return insert>0;
        }
        return false;
    }

    //判断一级分类是否存在
    //参数：一级分类名称
    private EduSubject existOneSubject(String oneSubjectName) {
        //根据一级分类名称查询数据库表
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",oneSubjectName);
        wrapper.eq("parent_id","0"); //一级分类parentId是0
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }

    //判断二级分类是否存在
    //参数：二级分类名称，parentId值
    private EduSubject existTwoSubject(String twoSubjectName,String parentId) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",twoSubjectName);
        wrapper.eq("parent_id",parentId);
        EduSubject eduSubject = baseMapper.selectOne(wrapper);
        return eduSubject;
    }
}
