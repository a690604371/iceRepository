package com.haitai.ice.largeUpload.service;

import com.haitai.ice.largeUpload.dao.FileResMapper;
import com.haitai.ice.utils.common.BaseService;
import com.haitai.ice.utils.domain.FileRes;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2015/10/12.
 */

@Service
public class FileResService extends BaseService<FileRes> {

    private FileResMapper fileResMapper;

    public FileResMapper getFileResMapper() {
        return fileResMapper;
    }

    @Resource(name = "fileResMapper")
    public void setFileResMapper(FileResMapper fileResMapper) {
        setMapper(fileResMapper);
        this.fileResMapper = fileResMapper;
    }



}
