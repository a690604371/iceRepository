package com.haitai.ice.largeUpload.dao;

import com.haitai.ice.largeUpload.entity.LargeUploadEntity;

import java.util.List;

public interface LargeUploadEntityMapper {

    int insert(LargeUploadEntity record);


    List<LargeUploadEntity> selectUsers();

    int deleteByPrimaryKey(Integer userid);


    int insertSelective(LargeUploadEntity record);

    LargeUploadEntity selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(LargeUploadEntity record);

    int updateByPrimaryKey(LargeUploadEntity record);

}
