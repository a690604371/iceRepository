package com.haitai.ice.abortAspect.service.impl;

import com.haitai.ice.abortAspect.annotation.ApiAnnotation;
import com.haitai.ice.abortAspect.bean.param.UserAddParam;
import com.haitai.ice.abortAspect.bean.result.Response;
import com.haitai.ice.abortAspect.service.UserInfoService;
import com.haitai.ice.scheduled.dynamic.task.ScheduledTask01;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 人员管理接口
 *
 * @author
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    private static final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);
    /**
     * 添加人员接口
     *
     * @author
     */
    @Override
    @ApiAnnotation(description = "添加人员")
    public Response addUser() {

        log.info("人员管理 UserInfoServiceImpl >> 添加人员接口开始");

        if (1 == 1) {
           throw  new RuntimeException("模拟异常了。。。。");
        }
        log.info("人员管理 UserInfoServiceImpl >> 添加人员接口结束");
        return Response.success();
    }
}