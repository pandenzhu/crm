package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(ActivityRemark record);

    int insertSelective(ActivityRemark record);

    ActivityRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ActivityRemark record);

    int updateByPrimaryKey(ActivityRemark record);

    /**
     * 根据activityId查询该市场活动下所有备注的明细信息
     *
     * @param activityId
     * @return
     */
    List<ActivityRemark> selectActivityForDetailByActivityId(String activityId);

    /**
     * 保存创建的市场活动备注
     *
     * @param activityRemark
     * @return
     */
    int insertActivityRemark(ActivityRemark activityRemark);

    /**
     * 根据id删除市场活动备注
     *
     * @param id
     * @return
     */
    int deleteActivityRemarkById(String id);

    /**
     * 保存修改的市场活动
     *
     * @param activityRemark
     * @return
     */
    int updateEditActivityRemarkById(ActivityRemark activityRemark);
}