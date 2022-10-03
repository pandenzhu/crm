package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {

    //查询市场活动备注信息
    List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId);

    //保存创建的市场活动
    int saveCreateActivityRemark(ActivityRemark activityRemark);

}
