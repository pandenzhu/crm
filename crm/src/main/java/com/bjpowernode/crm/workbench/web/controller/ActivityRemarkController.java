package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class ActivityRemarkController {

    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/saveCreateActivityRemark.do")
    public @ResponseBody Object saveCreateActivityRemark(ActivityRemark activityRemark, HttpSession session){
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        activityRemark.setId(UUIDUtils.getUUID());
        activityRemark.setCreateTime(DateUtils.dateTimeFormate(new Date()));
        activityRemark.setCreateBy(user.getId());
        activityRemark.setEditFlag(Contants.REMARK_EDIT_FLAG_NO_EDITED);

        ReturnObject returnObject = new ReturnObject();

        try {
            int ret =activityRemarkService.saveCreateActivityRemark(activityRemark);
            if (ret >0){
                returnObject.setCode(Contants.REMARK_EDIT_FLAG_YES_EDITED);
                returnObject.setRetData(activityRemark);
            }else{
                returnObject.setCode(Contants.REMARK_EDIT_FLAG_NO_EDITED);
                returnObject.setMsg("没修改");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.REMARK_EDIT_FLAG_NO_EDITED);
            returnObject.setMsg("没修改");
        }
        return returnObject;
    }
}
