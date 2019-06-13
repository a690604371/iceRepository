package com.haitai.ice.abortAspect.controller;

import com.haitai.ice.abortAspect.bean.param.MenuAddParam;
import com.haitai.ice.abortAspect.bean.param.UserAddParam;
import com.haitai.ice.abortAspect.bean.result.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * 菜单管理接口
 *
 * @author
 */
@RestController
@RequestMapping("/menu")
public class MenuInfoController {

    /**
     * 添加菜单
     *
     * @author
     */
    @PostMapping(value = "/add-menu")
    public Response addMenu() {
        return Response.success();
    }


}