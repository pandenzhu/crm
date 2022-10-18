package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    //查询所有用户
    List<Customer>queryAllCustomer();

    //保存创建的市场活动
    int saveCreateCustomer(Customer customer);

    //据条件分页查询客户的列表
    List<Customer> queryCustomerByConditionForPage(Map<String,Object>map);

    //根据条件查询客户的总条数
    int queryCountOfCustomerByCondition(Map<String,Object>map);
}
