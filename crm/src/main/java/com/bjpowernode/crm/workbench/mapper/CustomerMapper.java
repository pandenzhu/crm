package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerMapper {
    int deleteByPrimaryKey(String id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    /**
     * 保存创建的客户
     * @param customer
     * @return
     */
    int insertCustomer(Customer customer);

    /**
     * 查询所有客户
     * @return
     */
    List<Customer> selectAllCustomer();

    /**
     * 据条件分页查询客户的列表
     * @param map
     * @return
     */
    List<Customer> selectCustomerByConditionForPage(Map<String,Object> map);

    /**
     * 根据条件查询客户的总条数
     * @param map
     * @return
     */
    int selectCountOfCustomerByCondition(Map<String,Object>map);
}