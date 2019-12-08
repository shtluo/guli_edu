package cn.guliedu.eduvod.service.impl;

import cn.guliedu.eduvod.service.VodService;
import cn.guliedu.eduvod.utils.ConstantVodUtils;
import cn.guliedu.eduvod.utils.VodUtils;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    //上传视频到阿里云视频点播的方法
    @Override
    public String uploadVideoAliyun(MultipartFile file) {
        try {
            String accessKeyId = ConstantVodUtils.ACCESS_KEY_ID;
            String accessKeySecret = ConstantVodUtils.ACCESS_KEY_SECRET;
            String fileName = file.getOriginalFilename();//获取上传文件名称  01.99.0.1.jpg
            String title = fileName.substring(0, fileName.lastIndexOf("."));//上传之后文件名称
            InputStream inputStream = file.getInputStream(); //文件输入流
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();

            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = "";
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //删除阿里云视频功能
    @Override
    public void removeVideoAliyun(String videoId) {
        try {
            //1 创建初始化对象
            DefaultAcsClient client =
                    VodUtils.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
            //2 创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //3 向request设置视频id
            request.setVideoIds(videoId);
            //4 调用初始化对象方法实现视频删除
            client.getAcsResponse(request);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //删除多个视频
    @Override
    public void deleteMoreVideo(List<String> videoIds) {
        try {
            //1 创建初始化对象
            DefaultAcsClient client =
                    VodUtils.initVodClient(ConstantVodUtils.ACCESS_KEY_ID,ConstantVodUtils.ACCESS_KEY_SECRET);
            //2 创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //3 向request设置视频id    1,2,3
            //把list集合变成 1,2,3
            String ids = StringUtils.join(videoIds.toArray(), ",");
            request.setVideoIds(ids);
            //4 调用初始化对象方法实现视频删除
            client.getAcsResponse(request);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //根据视频id获取视频播放凭证
    @Override
    public String getPlayAuth(String videoId) {
        try {
            //1 创建初始化对象
            DefaultAcsClient client =
                    VodUtils.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //2 创建获取凭证request对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //3 向request设置视频id
            request.setVideoId(videoId);
            //4 调用方法实现获取
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            //5 response对象获取视频凭证
            String playAuth = response.getPlayAuth();
            return playAuth;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] arg) {
        List<String> ids = new ArrayList<>();
        ids.add("1111");
        ids.add("2222");
        ids.add("3333");
        // 1111,2222,3333
        String join = StringUtils.join(ids.toArray(), ",");
        System.out.println(join);

    }
}
