package com.leyili.controller;

import com.leyili.pojo.Result;
import com.leyili.utils.AliOSSUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/common")
public class UploadController {

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        log.info("文件上传,文件名:{}",file.getOriginalFilename());

        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        String fileName = UUID.randomUUID().toString()+suffix;

        try{
            file.transferTo(new File(new File("D:\\BaiduNetdiskDownload\\1 瑞吉外卖项目\\1 瑞吉外卖项目\\资料\\图片资源") + fileName));
        }catch (IOException e){
            e.printStackTrace();
        }

        return Result.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
            try{
                FileInputStream fileInputStream = new FileInputStream(new File("D:\\BaiduNetdiskDownload\\1 瑞吉外卖项目\\1 瑞吉外卖项目\\资料\\图片资源") + name);

                ServletOutputStream outputStream = response.getOutputStream();

                response.setContentType("image/jpeg");

                 int len = 0;
                 byte[] bytes = new byte[1024];
                 while ((len=fileInputStream.read(bytes))!=-1){
                     outputStream.write(bytes,0,len);
                     outputStream.flush();
                 }

                 outputStream.close();;
                 fileInputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
    }
}
