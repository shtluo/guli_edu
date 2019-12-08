package cn.guliedu.eduservice.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

//一级分类dto
@Data
public class OneSubjetDto {
    private String id; //一级分类id
    private String title; //一级分类名称

    //一个一级分类中有多个二级分类
    private List<TwoSubjetDto> children = new ArrayList<>();
}
