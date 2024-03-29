package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueService {

    //创建线索
    int saveCreateClue(Clue clue);

    //根据id查询线索详细信息
    Clue queryClueForDetailById(String id);

    //根据条件分页查询线索的列表
    List<Clue> queryClueByConditionForPage(Map<String, Object> map);

    //根据条件查询线索数量
    int queryCountClueByCondition(Map<String, Object> map);

    //根据id查询线索信息
    Clue queryClueById(String id);

    //更新线索
    int saveEditClue(Clue clue);

    //删除线索
    int deleteClueByIds(String[] id);

    //线索转换
    void saveConvertClue(Map<String,Object>map);
}
