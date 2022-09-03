package com.bjpowernode.crm.settings.web.controller;

import com.alibaba.druid.sql.visitor.functions.If;
import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    /**
     * url要和controller方法处理完请求后，响应信息返回的页面资源的目录保持一致
     */
    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin() {
        //请求转发到登录界面
        return "settings/qx/user/login";
    }

    @Autowired
    private UserService userService;

    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody
    Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpServletResponse response,HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        //调用service方法，查询用户
        User user = userService.queryUserByLoginActAndLoginPwd(map);

        ReturnObject returnObject = new ReturnObject();

        //根据查询结果，生成响应信息
        if (user == null) {
            //登陆失败，用户名或者密码错误
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMsg("用户名或者密码错误");
        } else {//进一步判断是否合法
            //登陆失败，账号过期
            if (DateUtils.dateTimeFormate(new Date()).compareTo(user.getExpireTime()) > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMsg("登陆失败，账号过期");
                //登陆失败，IP受限  获取用户IP--request.getRemoteAddr()
            } else if (!user.getAllowIps().contains(request.getRemoteAddr())) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMsg("登陆失败，IP受限");
                //登录失败状态被锁定
            } else if ("0".equals(user.getLockState())) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMsg("登录失败状态被锁定");
            } else {
                //登录成功
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                //把user放到session中
                session.setAttribute(Contants.SESSION_USER, user);

                //如果需要记住密码，则需要写cookie
                if ("true".equals(isRemPwd)){
                    Cookie c1=new Cookie("loginAct",user.getLoginAct());
                    c1.setMaxAge(10*24*60*60);
                    response.addCookie(c1);
                    Cookie c2=new Cookie("loginPwd",user.getLoginPwd());
                    c2.setMaxAge(10*24*60*60);
                    response.addCookie(c2);
                }else {
                    //如果不需要记住密码则清空cookie
                    Cookie c1=new Cookie("loginAct","1");
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                    Cookie c2=new Cookie("loginPwd","1");
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }
        }
        return returnObject;
    }

    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpServletResponse response, HttpSession session) {
        //清空cookie
        Cookie c1 = new Cookie("loginAct", "1");
        c1.setMaxAge(0);
        response.addCookie(c1);
        Cookie c2 = new Cookie("loginPwd", "1");
        c2.setMaxAge(0);
        response.addCookie(c2);
        //销毁cookie
        session.invalidate();
        //跳转到首页
        return "redirect:/";//借助springmvc来重定向,response.sendRedirect("/crm/");
    }
}
