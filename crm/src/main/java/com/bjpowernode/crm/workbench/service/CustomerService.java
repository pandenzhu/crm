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

    //根据id查询市场活动信息
    Customer queryCustomerById(String id);

    //修改客户
    int saveEditCustomer(Customer customer);

    //删除客户
    int deleteCustomerById(String[] id);

    //查询客户详细信息
    Customer queryCustomerForDetailById(String id);

    //查询所有客户名称
    Customer queryCustomerByName(String name);

    List<String> queryCustomerNameByCustomerName(String name);
}
