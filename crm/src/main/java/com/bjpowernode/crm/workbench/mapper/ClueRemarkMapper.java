package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.ClueRemark;

import java.util.List;


public interface ClueRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(ClueRemark record);

    int insertSelective(ClueRemark record);

    ClueRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClueRemark record);

    int updateByPrimaryKey(ClueRemark record);

    /**
     * 根据clueId查询该线索下的备注详情
     *
     * @param clueId
     * @return
     */
    List<ClueRemark> selectClueRemarkForDetailByClueId(String clueId);

    /**
     * 保存创建的线索备注
     * @param clueRemark
     * @return
     */
    int insertClueRemark(ClueRemark clueRemark);

    /**
     * 根据clueId查询线索所有备注
      * @param
     * @return
     */
    /*List<ClueRemark> selectClueRemarkListByClueId(String clueId);*/
}