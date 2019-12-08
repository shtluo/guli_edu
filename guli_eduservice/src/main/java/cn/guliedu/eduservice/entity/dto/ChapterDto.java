package cn.guliedu.eduservice.entity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterDto {
    private String id;
    private String title;
    //一个章节有很多小节
    private List<VideoDto> children = new ArrayList<>();
}
