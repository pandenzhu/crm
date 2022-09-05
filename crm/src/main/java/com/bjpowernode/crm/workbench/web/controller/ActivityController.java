package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ActivityController {

    @Autowired
    private UserService userService;

    @RequestMapping("workbench/activity/index.do")
    public String activityIndex(HttpServletRequest request) {
        //调用service层方法，查询所有用户
        List<User> userList = userService.queryAllUsers();
        //把查询到的数据保存到request中
        request.setAttribute("userList", userList);
        //请求转发到市场活动界面
        return "workbench/activity/index";
    }
}
