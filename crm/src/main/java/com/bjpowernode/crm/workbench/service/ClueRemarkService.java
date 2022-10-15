package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkService {

    //根据clueId查询该线索下的备注详情
    List<ClueRemark> queryClueRemarkForDetailByClueId(String clueId);

    //保存创建的线索备注
    int saveCreateClueRemark(ClueRemark remark);

/*    //根据clueId查询线索所有备注
    List<ClueRemark> queryClueRemarkByClueId(String clueId);*/
}
