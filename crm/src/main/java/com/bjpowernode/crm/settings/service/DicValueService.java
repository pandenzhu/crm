package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicValue;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DicValueService {

    //根据typeCode查询数据字典值
   List<DicValue> queryDicValueByTypeCode(String typeCode);

}
