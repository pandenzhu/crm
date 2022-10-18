package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Customer;

import java.util.List;

public interface CustomerService {

    //查询所有用户
    List<Customer>queryAllCustomer();

    //保存创建的市场活动
    int saveCreateCustomer(Customer customer);

}
