package com.haitai.ice.largeUpload.service;

import com.github.pagehelper.PageInfo;
import com.haitai.ice.largeUpload.entity.LargeUploadEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

public interface LargeUploadService {

    int addUser(LargeUploadEntity user);

    PageInfo<LargeUploadEntity> findAllUser(int pageNum, int pageSize);

    /**
     * 上传文件
     * @param request
     * @param multipartFile
     * @return
     */
    Map<String, Object> upload(HttpServletRequest request, MultipartFile multipartFile) throws IOException;

    /**
     * 上传文件前校验
     * @param request
     * @return
     * @throws IOException
     */
    Map<String, Object> isUpload(HttpServletRequest request);







}
