package com.haitai.ice.largeUpload.controller;

import com.haitai.ice.largeUpload.entity.LargeUploadEntity;
import com.haitai.ice.largeUpload.service.LargeUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
//@Scope("prototype")
@RequestMapping(value = "/largeUpload")
public class LargeUploadController {

    @Autowired
    private LargeUploadService largeUploadService;

    @ResponseBody
    @PostMapping("/add")
    public int addUser(LargeUploadEntity user){

        return largeUploadService.addUser(user);
    }

    /**
     * 测试方法
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @GetMapping("/all")
    public Object findAllUser(
            @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                    int pageNum,
            @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                    int pageSize){
        return largeUploadService.findAllUser(pageNum,pageSize);
    }

    /**
     *上传文件
     * @param request
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "/upload")
    public Map<String, Object> upload(HttpServletRequest request,
                                      @RequestParam(value = "data",required = false) MultipartFile multipartFile) throws Exception {

        return largeUploadService.upload(request,multipartFile);

    }

    /**
     * 上传文件前校验
     *
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/isUpload")
    public Map<String, Object> isUpload(HttpServletRequest request){

        return largeUploadService.isUpload(request);

    }




















}
