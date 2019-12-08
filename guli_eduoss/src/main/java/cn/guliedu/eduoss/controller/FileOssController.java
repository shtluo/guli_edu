package cn.guliedu.eduoss.controller;

import cn.guliedu.common.R;
import cn.guliedu.eduoss.service.FileOssService;
import cn.guliedu.eduoss.utils.ConstantPropertiesUtil;
import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/eduoss/oss")
@CrossOrigin
public class FileOssController {

    @Autowired
    private FileOssService ossService;

    //上传头像到阿里云oss方法
    @PostMapping("fileupload")
    public R uploadFileOss(MultipartFile file) {
        String url = ossService.uploadFileOss(file);
        return R.ok().data("url",url);
    }

}
