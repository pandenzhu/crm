package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.CustomerRemark;

import java.util.List;

public interface CustomerRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomerRemark record);

    int insertSelective(CustomerRemark record);

    CustomerRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerRemark record);

    int updateByPrimaryKey(CustomerRemark record);

    /**
     * 批量保存客户备注
     * @param list
     * @return
     */
    int insertCustomerRemarkByList(List<CustomerRemark>list);
}