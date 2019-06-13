package com.haitai.ice.abortAspect.controller;

import com.haitai.ice.abortAspect.bean.param.UserAddParam;
import com.haitai.ice.abortAspect.bean.result.Response;
import com.haitai.ice.abortAspect.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 人员管理接口
 *
 * @author
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 添加人员
     *
     * @author
     */
    @PostMapping(value = "/addUser")
    public Response addUser() {
        return userInfoService.addUser();
    }

}