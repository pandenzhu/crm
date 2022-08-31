package com.bjpowernode.crm.settings.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    /**
     * url要和controller方法处理完请求后，响应信息返回的页面资源的目录保持一致
     */
    @RequestMapping("settings/qx/user/toLogin.do")
    public String toLogin(){
        //请求转发到登录界面
        return "settings/qx/user/login";
    }
}
