package com.haitai.ice.abortAspect.controller;

import com.haitai.ice.abortAspect.bean.param.UserAddParam;
import com.haitai.ice.abortAspect.bean.result.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 切面测试
 *
 * @author
 */
@RestController
@RequestMapping("/aspect")
public class AspectExampleController {
    /**
     * 切面测试
     *
     * @author
     */
    @PostMapping(value = "/test")
    public Response test() {
        return Response.success();
    }

}