package cn.guliedu.eduoss.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileOssService {

    public String uploadFileOss(MultipartFile file);
}
