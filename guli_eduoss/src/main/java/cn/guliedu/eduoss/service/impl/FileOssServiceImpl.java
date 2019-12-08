package cn.guliedu.eduoss.service.impl;

import cn.guliedu.eduoss.service.FileOssService;
import cn.guliedu.eduoss.utils.ConstantPropertiesUtil;
import com.aliyun.oss.OSSClient;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class FileOssServiceImpl implements FileOssService {
    @Override
    public String uploadFileOss(MultipartFile file) {
        try {
            //1 获取上传的文件 MultipartFile  <input type="file" name="file"/>
            //2 创建OSSClient实例  2.8.3版本 OSSClient
            OSSClient client = new OSSClient(ConstantPropertiesUtil.END_POINT, ConstantPropertiesUtil.ACCESS_KEY_ID,ConstantPropertiesUtil.ACCESS_KEY_SECRET);
            String originalFilename = file.getOriginalFilename();
            //在文件名称里面添加uuid值
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            // 01.jpg
            String fileName = uuid+originalFilename;
            InputStream inputStream = file.getInputStream();
            //3 调用方法实现上传
            //第一个参数 bucketname
            //第二个参数 文件位置和名称   /2019/10/26/01.jpg
            //第三个参数 文件输入流
            //根据日期进行分类，获取当前日期   /2019/10/26
            String dateUrl = new DateTime().toString("yyyy/MM/dd");
            fileName = dateUrl+"/"+fileName;

            client.putObject(ConstantPropertiesUtil.BUCKET_NAME,fileName,inputStream);
            //4 关闭OSSClient
            client.shutdown();

            //返回上传之后阿里云oss地址
            // https://edu-demo0603.oss-cn-beijing.aliyuncs.com/01.jpg
            String url = "https://"+ConstantPropertiesUtil.BUCKET_NAME+"."+ConstantPropertiesUtil.END_POINT+"/"+fileName;
            return url;
        }catch(Exception e) {
            return null;
        }
    }
}
