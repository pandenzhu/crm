package com.bjpowernode.crm.workbench.service.Impl;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.mapper.ActivityMapper;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public int saveCreateActivity(Activity activity) {
        return activityMapper.insertActivity(activity);
    }

    //根据条件分页查询市场活动列表
    @Override
    public List<Activity> queryActivityByConditionForPage(Map<String, Object> map) {
        return activityMapper.selectActivityByConditionForPage(map);
    }

    //根据条件查询市场活动的总条数
    @Override
    public int selectCountActivityByCondition(Map<String, Object> map) {
        return activityMapper.selectCountActivityByCondition(map);
    }

    //根据ids删除市场活动
    @Override
    public int deleteActivityByIds(String[] ids) {
        return activityMapper.deleteActivityByIds(ids);
    }

    //根据id查询市场活动信息
    @Override
    public Activity queryActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    //保存创建的市场活动
    @Override
    public int saveEditActivity(Activity activity) {
        return activityMapper.updateActivity(activity);
    }

    //查询所有市场活动
    @Override
    public List<Activity> queryAllActivitys() {
        return activityMapper.selectAllActivitys();
    }

    //查询选择导出的市场活动
    @Override
    public List<Activity> querySelectActivitysByIds(String[] id) {
        return activityMapper.selectActivitysByIds(id);
    }

    //导入市场活动
    @Override
    public int saveCreateActivityList(List<Activity> activityList) {
        return activityMapper.insertActivityList(activityList);
    }

    //根据id查询市场活动的明细信息
    @Override
    public Activity queryActivityDetailById(String id) {
        return activityMapper.selectActivityDetailById(id);
    }

    //根据clueId查询该线索相关的市场活动的明细信息
    @Override
    public List<Activity> queryActivityForDetailByClueId(String clueId) {
        return activityMapper.selectActivityForDetailByClueId(clueId);
    }

    //根据name模糊查询市场活动，并且把已经跟clueId关联过的市场活动排除
    @Override
    public List<Activity> queryActivityForDetailByNameClueId(Map<String, Object> map) {
        return activityMapper.selectActivityForDetailByNameClueId(map);
    }

    @Override
    public List<Activity> queryActivityForDetailByIds(String[] ids) {
        return activityMapper.selectActivityForDetailByIds(ids);
    }

    @Override
    public List<Activity> queryActivityForConvertByNameClueId(Map<String, Object> map) {
        return activityMapper.selectActivityForConvertByNameClueId(map );
    }
}
