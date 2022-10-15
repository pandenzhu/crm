package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.ClueRemark;
import com.bjpowernode.crm.workbench.service.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

public class ClueRemarkController {

    @Autowired
    private ClueRemarkService clueRemarkService;

    /**
     * 保存创建的线索备注
     * @param clueRemark
     * @param session
     * @return
     */
    @RequestMapping("/workbench/clue/saveCreateClueRemark.do")
    public @ResponseBody
    Object saveCreateClueRemark(ClueRemark clueRemark, HttpSession session) {
        User user = (User) session.getAttribute(Contants.SESSION_USER);

        //封装参数
        clueRemark.setId(UUIDUtils.getUUID());
        clueRemark.setCreateBy(user.getId());
        clueRemark.setCreateTime(DateUtils.dateTimeFormate(new Date()));
        clueRemark.setEditFlag(Contants.REMARK_EDIT_FLAG_NO_EDITED);

        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = clueRemarkService.saveCreateClueRemark(clueRemark);
            if (ret > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(clueRemark);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMsg("添加失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMsg("添加失败");
        }
        return returnObject;
    }

   /* @RequestMapping("/workbench/clue/queryClueRemarkListByClueId.do")
    public @ResponseBody Object queryClueRemarkListByClueId(String clueId){
        List<ClueRemark> clueRemarkList = clueRemarkService.queryClueRemarkByClueId(clueId);
        return clueRemarkList;
    }*/


}
