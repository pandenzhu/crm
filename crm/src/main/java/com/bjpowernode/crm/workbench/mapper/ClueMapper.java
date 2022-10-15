package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueMapper {
    int deleteByPrimaryKey(String id);

    int insert(Clue record);

    int insertSelective(Clue record);

    Clue selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Clue record);

    int updateByPrimaryKey(Clue record);

    /**
     * 创建线索
     * @param clue
     * @return
     */
    int insertClue(Clue clue);

    /**
     * 根据id查询线索详细信息
     * @param id
     * @return
     */
    Clue selectClueForDetailById(String id);

    /**
     * 根据条件分页查询线索的列表
     * @param map
     * @return
     */
    List<Clue> selectClueByConditionForPage(Map<String,Object> map);

    /**
     *根据条件查询线索数量
     * @param map
     * @return
     */
    int selectCountClueByCondition(Map<String,Object>map);

    /**
     * 根据id查询线索信息
     * @param id
     * @return
     */
    Clue selectClueById(String id);

    /**
     * 更新线索
     * @param clue
     * @return
     */
    int updateClue(Clue clue);

    /**
     * 根据id删除线索
     * @param id
     * @return
     */
    int deleteClueByIds(String[] id);



}