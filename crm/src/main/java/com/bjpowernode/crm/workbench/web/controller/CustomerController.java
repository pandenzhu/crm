package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Contacts;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.CustomerRemark;
import com.bjpowernode.crm.workbench.service.CustomerRemarkService;
import com.bjpowernode.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerRemarkService customerRemarkService;

    @RequestMapping("/workbench/customer/index.do")
    public String indexCustomer(HttpServletRequest request) {
        List<User> userList = userService.queryAllUsers();
        // List<Customer>customerList=customerService.queryAllCustomer();
        // request.setAttribute("customerList",customerList);
        request.setAttribute("userList", userList);
        return "workbench/customer/index";
    }

    /**
     * 保存创建客户
     *
     * @param customer
     * @param session
     * @return
     */
    @RequestMapping("/workbench/customer/saveCreateCustomer.do")
    public @ResponseBody
    Object saveCreateCustomer(Customer customer, HttpSession session) {
        User user = (User) session.getAttribute(Contants.SESSION_USER);

        //封装参数
        customer.setId(UUIDUtils.getUUID());
        customer.setCreateBy(user.getId());
        customer.setCreateTime(DateUtils.dateTimeFormate(new Date()));

        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = customerService.saveCreateCustomer(customer);
            if (ret > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(ret);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMsg("创建失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMsg("创建失败");
        }
        return returnObject;
    }

    /**
     * 分页查询
     *
     * @param name
     * @param owner
     * @param phone
     * @param website
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping("/workbench/customer/queryCustomerByConditionForPage.do")
    public @ResponseBody
    Object queryCustomerByConditionForPage(String name, String owner, String phone, String website, int pageNo, int pageSize) {

        //封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("phone", phone);
        map.put("website", website);
        map.put("beginNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        //调用service层方法，查询数据
        List<Customer> customerList = customerService.queryCustomerByConditionForPage(map);
        int totalRows = customerService.queryCountOfCustomerByCondition(map);
        //根据查询结果，生成相应信息
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("customerList", customerList);
        retMap.put("totalRows", totalRows);
        return retMap;
    }

    @RequestMapping("/workbench/customer/queryCustomerById.do")
    public @ResponseBody
    Object queryCustomerById(String id) {
        Customer customer = customerService.queryCustomerById(id);
        return customer;
    }

    @RequestMapping("/workbench/customer/saveEditCustomer.do")
    public @ResponseBody
    Object saveEditCustomer(Customer customer, HttpSession session) {

        User user = (User) session.getAttribute(Contants.SESSION_USER);
        customer.setEditBy(user.getId());
        customer.setEditTime(DateUtils.dateTimeFormate(new Date()));

        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = customerService.saveEditCustomer(customer);
            if (ret > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(ret);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMsg("修改失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMsg("修改失败");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/customer/deleteCustomerIds.do")
    public @ResponseBody
    Object deleteCustomerIds(String[] id, HttpSession session) {

        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = customerService.deleteCustomerById(id);
            if (ret > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(ret);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMsg("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMsg("删除失败");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/customer/detailCustomer.do")
    public @ResponseBody
    Object detailCustomer(String id, HttpServletRequest request) {
        ReturnObject returnObject = new ReturnObject();
        Customer customer = customerService.queryCustomerForDetailById(id);
        List<CustomerRemark> customerRemarkList = customerRemarkService.queryCustomerRemarkForDetailByCustomerId(id);
        request.setAttribute("customer", customer);
        request.setAttribute("customerRemarkList", customerRemarkList);
        return "workbench/customer/detail";
    }
}
