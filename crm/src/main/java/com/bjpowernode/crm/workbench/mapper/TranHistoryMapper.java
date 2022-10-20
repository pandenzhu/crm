package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(TranHistory record);

    int insertSelective(TranHistory record);

    TranHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TranHistory record);

    int updateByPrimaryKey(TranHistory record);

    /**
     * 保存创建的交易历史
     * @param tranHistory
     * @return
     */
    int insertTranHistory(TranHistory tranHistory);

    /**
     * 根据tranId查询历史交易
     * @param tranId
     * @return
     */
    List<TranHistory> selectTranHistoryForDetailByTranId(String tranId);
}