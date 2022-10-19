package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TranController {

    @Autowired
    private DicValueService dicValueService;

    @RequestMapping("/workbench/transaction/index.do")
    public String index(HttpServletRequest request){
        List<DicValue>stageList =dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue>transactionTypeList =dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue>sourceList =dicValueService.queryDicValueByTypeCode("source");
        request.setAttribute("stageList",stageList);
        request.setAttribute("transactionTypeList",transactionTypeList);
        request.setAttribute("sourceList",sourceList);
        return "workbench/transaction/index";
    }
}
