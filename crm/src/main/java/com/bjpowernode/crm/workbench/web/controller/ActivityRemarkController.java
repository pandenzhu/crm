package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class ActivityRemarkController {

    @Autowired
    private ActivityRemarkService activityRemarkService;

    /**
     * 保存创建的市场活动备注
     *
     * @param activityRemark
     * @param session
     * @return
     */
    @RequestMapping("/workbench/activity/saveCreateActivityRemark.do")
    public @ResponseBody
    Object saveCreateActivityRemark(ActivityRemark activityRemark, HttpSession session) {
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        activityRemark.setId(UUIDUtils.getUUID());
        activityRemark.setCreateTime(DateUtils.dateTimeFormate(new Date()));
        activityRemark.setCreateBy(user.getId());
        activityRemark.setEditFlag(Contants.REMARK_EDIT_FLAG_NO_EDITED);

        ReturnObject returnObject = new ReturnObject();

        try {
            //调用service层方法，保存创建的市场活动备注
            int ret = activityRemarkService.saveCreateActivityRemark(activityRemark);
            if (ret > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(activityRemark);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMsg("创建失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMsg("创建失败");
        }
        return returnObject;
    }

    /**
     * 根据id删除市场活动备注
     *
     * @param id
     * @return
     */
    @RequestMapping("/workbench/activity/deleteActivityRemarkById.do")
    public @ResponseBody
    Object deleteActivityRemarkById(String id) {

        ReturnObject returnObject = new ReturnObject();

        try {
            int ret = activityRemarkService.deleteActivityRemarkById(id);
            if (ret > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setMsg("删除成功");
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMsg("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMsg("删除失败");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/activity/saveEditActivityRemark.do")
    public @ResponseBody Object saveEditActivityRemark(ActivityRemark activityRemark,HttpSession session){
        User user= (User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        activityRemark.setEditFlag(Contants.REMARK_EDIT_FLAG_YES_EDITED);
        activityRemark.setEditBy(user.getId());
        activityRemark.setEditTime(DateUtils.dateTimeFormate(new Date()));

        ReturnObject returnObject = new ReturnObject();

        try {
            //调用service层方法，保存修改的市场活动备注
            int ret = activityRemarkService.saveEditActivityRemark(activityRemark);
            if (ret >0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(activityRemark);
            }else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMsg("修改保存失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMsg("修改保存失败");
        }
       return returnObject;
    }
}
