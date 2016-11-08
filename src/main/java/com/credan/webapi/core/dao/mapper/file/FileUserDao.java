package com.credan.webapi.core.dao.mapper.file;

import com.credan.webapi.config.mybatis.conf.MyBatisDao;
import com.credan.webapi.core.dao.entity.file.FileUser;

public interface FileUserDao extends MyBatisDao<FileUser>{
	
	FileUser findOne(FileUser fileUser);
}