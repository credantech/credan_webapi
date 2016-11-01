package com.credan.webapi.core.dao.mapper.share;

import com.credan.webapi.core.dao.entity.share.MobileLabel;

public interface MobileLabelMapper {
    int deleteByPrimaryKey(String id);

    int insert(MobileLabel record);

    int insertSelective(MobileLabel record);

    MobileLabel selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(MobileLabel record);

    int updateByPrimaryKey(MobileLabel record);
}