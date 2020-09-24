package com.newxton.oss.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author soyojo.earth@gmail.com
 * @time 2020/8/11
 * @address Shenzhen, China
 */
@RestController
public class NxtUploadImageController {

    @Value("${newxton.config.oss.pathPublic}")
    private String ossPathPublic;

    @Value("${newxton.config.oss.secretKey}")
    private String ossSecretKey;

    /**
     * 上传图片
     */
    @RequestMapping(value = "/upload/public_pic", method = RequestMethod.POST)
    public Map<String, Object> index(
            @RequestHeader("Content-Length") Long contentLength,
            @RequestParam(value = "file",required = false) MultipartFile multipartFile,
            @RequestParam("Secret-Key") String secretKey
    ) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", 0);
        result.put("message", "");

        if (!secretKey.equals(this.ossSecretKey)){
            result.put("status", 72);
            result.put("message", "授权码错误，禁止上传");
            return result;
        }

        if (multipartFile != null && !multipartFile.isEmpty()) {

            // 保存图片
            String originalFilename = multipartFile.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
            String savePath = new SimpleDateFormat("/yyyy/MM/dd").format(new Date(System.currentTimeMillis()));
            String saveFilename = String.valueOf(new Random().nextInt(1000000000)+1000000000)+"."+suffix;
            this.createDir(this.ossPathPublic + savePath);
            try {
                String url = savePath + "/" + saveFilename;
                String filePath = this.ossPathPublic + savePath + "/" + saveFilename;
                File file = new File(filePath);
                byte[] bytes = multipartFile.getBytes();
                OutputStream os = new FileOutputStream(file);
                os.write(bytes);
                os.close();
                result.put("pic_path",url);
            }
            catch (Exception e) {
                System.out.println("Exception: " + e);
                /*上传失败*/
                result.put("status", 73);
                result.put("message", "上传失败");
            }

        }

        return result;

    }

    public boolean createDir(String destDirName) {
        File dir = new File(destDirName);
        if (dir.exists()) {
//            System.out.println(destDirName + "目标目录已经存在");
            return false;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录
        if (dir.mkdirs()) {
//            System.out.println("创建目录" + destDirName + "成功！");
            return true;
        } else {
            System.out.println("创建目录" + destDirName + "失败！");
            return false;
        }
    }

}
