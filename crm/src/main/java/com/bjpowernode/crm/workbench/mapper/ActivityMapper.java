package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    int deleteByPrimaryKey(String id);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);

    /**
     * 保存创建的市场活动
     * @param activity
     * @return
     */
    int insertActivity(Activity activity);

    /**
     * 根据条件分页查询市场活动列表
     * @param map
     * @return
     */
    List<Activity>selectActivityByConditionForPage(Map<String,Object>map);

    /**
     *根据条件查询市场活动的总条数
     * @param map
     * @return
     */
    int selectCountActivityByCondition(Map<String,Object>map);

    /**
     * 根据ids删除市场活动
     * @param ids
     * @return
     */
    int deleteActivityByIds(String[] ids);

    /**
     * 根据id查询市场活动信息
     * @param id
     * @return
     */
    Activity selectActivityById(String id);

    /**
     * 保存修改的市场活动
     * @param activity
     * @return
     */
    int updateActivity(Activity activity);

    /**
     * 查询所有的市场活动
     * @return
     */
    List<Activity>selectAllActivitys();

    /**
     * 根据ids选择导出市场活动
     * @param id
     * @return
     */
    List<Activity>selectActivitysByIds(String[] id);

}