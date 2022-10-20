package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.TranRemark;

import java.util.List;

public interface TranRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(TranRemark record);

    int insertSelective(TranRemark record);

    TranRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TranRemark record);

    int updateByPrimaryKey(TranRemark record);

    /**
     * 批量保存创建的交易备注
     * @param list
     * @return
     */
    int insertTranRemarkByList(List<TranRemark>list);

    /**
     * 根据tranId查询交易备注的详细信息
     * @param tranId
     * @return
     */
    List<TranRemark> selectTranRemarkForDetailByTranId(String tranId);
}