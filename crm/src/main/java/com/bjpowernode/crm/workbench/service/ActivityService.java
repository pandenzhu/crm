package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    //保存创建的市场活动
    int saveCreateActivity(Activity activity);

    //根据条件分页查询市场活动列表
    List<Activity> queryActivityByConditionForPage(Map<String, Object> map);

    //根据条件查询市场活动的总条数
    int selectCountActivityByCondition(Map<String, Object> map);

    //根据ids删除市场活动
    int deleteActivityByIds(String[] ids);

    //根据id查询市场活动信息
    Activity queryActivityById(String id);

    //保存更新的市场活动
    int saveEditActivity(Activity activity);

    //查询所有的市场活动
    List<Activity> queryAllActivitys();

    //查询选择导出的市场活动
    List<Activity> querySelectActivitysByIds(String[] id);

    //批量保存导入的市场活动
    int saveCreateActivityList (List<Activity>activityList);

    //根据id查询市场活动的明细信息
    Activity queryActivityDetailById(String id);
}
