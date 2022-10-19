package com.bjpowernode.crm.workbench.service.Impl;

import com.bjpowernode.crm.workbench.domain.CustomerRemark;
import com.bjpowernode.crm.workbench.mapper.CustomerRemarkMapper;
import com.bjpowernode.crm.workbench.service.CustomerRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("customerRemarkService")
public class CustomerRemarkServiceImpl implements CustomerRemarkService {

    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;
    @Override
    public List<CustomerRemark> queryCustomerRemarkForDetailByCustomerId(String customerId) {
        return customerRemarkMapper.selectCustomerRemarkForDetailByCustomerId(customerId);
    }
}
