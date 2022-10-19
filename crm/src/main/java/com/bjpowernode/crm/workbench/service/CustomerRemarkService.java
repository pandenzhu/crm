package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.CustomerRemark;

import java.util.List;

public interface CustomerRemarkService {

    List<CustomerRemark> queryCustomerRemarkForDetailByCustomerId(String customerId);
}
