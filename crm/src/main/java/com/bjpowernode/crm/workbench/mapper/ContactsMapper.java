package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.workbench.domain.Contacts;

public interface ContactsMapper {
    int deleteByPrimaryKey(String id);

    int insert(Contacts record);

    int insertSelective(Contacts record);

    Contacts selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Contacts record);

    int updateByPrimaryKey(Contacts record);

    /**
     * 保存创建联系人
     * @param contacts
     * @return
     */
    int insertContacts(Contacts contacts);
}