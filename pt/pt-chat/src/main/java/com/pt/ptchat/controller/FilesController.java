package com.pt.ptchat.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * 文件上传          成功！！！！！！
 */
@RestController
public class FilesController {
    @GetMapping(value = "/files")
    public String file() {
        return "file";
    }

    @PostMapping(value = "/api/fileUpload")
    public String fileUpload(@RequestParam(value = "file") MultipartFile file, Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀
        String filePath = "D://temp-rainy//avatars//"; // 上传后的路径
        if(suffixName=="wav"){
            String duration = fileName.split("\\.")[0];
            fileName = UUID.randomUUID() + "$" + duration + suffixName; // 新文件名
        }else {
            fileName = UUID.randomUUID() + "$" + fileName; // 新文件名
        }

        System.out.println("file"+fileName);
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imgUrl = "http://localhost:9998/chat/file/" + fileName;
        //String filename = "D:/temp-rainy/avatars/" + fileName;
//        User user = userService.findUserByUsername("111111");
//        user.setAvatar(imgUrl);
//        userService.save(user);
        //model.addAttribute("filename", filename);
        return imgUrl;//"成功上传图片";imgUrl
    }
    @GetMapping("/api/fileDownload")//@RequestParam(name = "fileName") String fileName,
    public String fileDownload (String fileName,HttpServletRequest request, HttpServletResponse response){
        //String fileName= "";
        //String fileName = request.getParameter("fileName");
        //System.out.println(fileName);

        String filePath = "D://temp-rainy//avatars//";

        OutputStream os = null;
        InputStream is= null;
        try {
            // 取得输出流
            os = response.getOutputStream();
            // 清空输出流
            response.reset();
           // response.setContentType("application/force-download;charset=GBK");
            response.setContentType("application/octet-stream;charset=UTF-8");

            response.addHeader("Content-Disposition","attachment;fileName="+fileName);
           // response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("utf-8"), "iso-8859-1"));


            //读取流
            File f = new File(filePath+fileName);
            is = new FileInputStream(f);
            if (is == null) {
                System.out.println("下载附件失败，请检查文件“" + fileName + "”是否存在");
                return "下载附件失败，请检查文件“" + fileName + "”是否存在";
            }
            //复制
            IOUtils.copy(is, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            return "下载附件失败,error:"+e.getMessage();
        }
        //文件的关闭放在finally中
        finally
        {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "ok";


    }
}
