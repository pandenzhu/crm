package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.DicValueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.domain.TranRemark;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranHistoryService;
import com.bjpowernode.crm.workbench.service.TranRemarkService;
import com.bjpowernode.crm.workbench.service.TranService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

@Controller
public class TranController {

    @Autowired
    private DicValueService dicValueService;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TranService tranService;

    @Autowired
    private TranRemarkService tranRemarkService;

    @Autowired
    private TranHistoryService tranHistoryService;

    @RequestMapping("/workbench/transaction/index.do")
    public String index(HttpServletRequest request) {
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        request.setAttribute("stageList", stageList);
        request.setAttribute("transactionTypeList", transactionTypeList);
        request.setAttribute("sourceList", sourceList);
        return "workbench/transaction/index";
    }

    @RequestMapping("/workbench/transaction/toSave.do")
    public String toSave(HttpServletRequest request) {

        List<User> userList = userService.queryAllUsers();
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        request.setAttribute("stageList", stageList);
        request.setAttribute("transactionTypeList", transactionTypeList);
        request.setAttribute("sourceList", sourceList);
        request.setAttribute("userList", userList);

        return "workbench/transaction/save";
    }

    @RequestMapping("/workbench/transaction/getPossibilityByStage.do")
    public @ResponseBody
    Object getPossibilityByStage(String stageValue) {
        //解析properties配置文件，根据阶段获取可能性
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility = bundle.getString(stageValue);
        //返回相应信息
        return possibility;
    }

    @RequestMapping("/workbench/transaction/queryCustomerNameByName.do")
    public @ResponseBody
    Object queryCustomerNameByName(String customerName) {
        //调用service层方法，查询客户名称
        List<String> customerNameList = customerService.queryCustomerNameByCustomerName(customerName);
        //根据查询结果，返回相应信息
        return customerNameList;
    }

    @RequestMapping("/workbench/transaction/saveCreateTran.do")
    public @ResponseBody
    Object saveCreateTran(@RequestParam Map<String, Object> map, HttpSession session) {
        //封装参数
        map.put(Contants.SESSION_USER, session.getAttribute(Contants.SESSION_USER));

        ReturnObject returnObject = new ReturnObject();
        try {
            //调用service，保存创建的交易
            tranService.saveCreateTran(map);
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMsg("创建失败");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/transaction/detailTran.do")
    public String detailTran(String id, HttpServletRequest request) {
        //调用service层方法，查询数据
        Tran tran = tranService.queryTranDetailById(id);
        List<TranRemark> tranRemarkList = tranRemarkService.queryTranRemarkServiceForDetailByTranId(id);
        List<TranHistory> tranHistoryList = tranHistoryService.queryTranHistoryForDetailByTranId(id);

        //根据tran所处阶段名称查询可能性
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possibility = bundle.getString(tran.getStage());
        tran.setPossibility(possibility);

        //把数据保存到request中
        request.setAttribute("tran", tran);
        request.setAttribute("tranRemarkList", tranRemarkList);
        request.setAttribute("tranHistoryList", tranHistoryList);

        //调用service层方法，查询交易的所有阶段
        List<DicValue>stageList = dicValueService.queryDicValueByTypeCode("stage");
        request.setAttribute("stageList",stageList);
        //请求转发
        return "workbench/transaction/detail";
    }


}
