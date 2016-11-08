/**
 * @(#) FileUserService.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service.file;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.credan.webapi.comm.ResultVo;
import com.credan.webapi.comm.enums.ConstantEnums.FileClassifyEnum;
import com.credan.webapi.comm.enums.DelFlagEnum;
import com.credan.webapi.config.Global;
import com.credan.webapi.config.jersey.api.exception.ParamException;
import com.credan.webapi.core.dao.entity.file.FileInfo;
import com.credan.webapi.core.dao.entity.file.FileUser;
import com.credan.webapi.core.dao.mapper.file.FileUserDao;
import com.credan.webapi.core.service.BasicService;
import com.google.common.base.Preconditions;

/**
 * 
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年11月8日 上午10:23:16 $
 */
@Service
public class FileUserService extends BasicService<FileUserDao, FileUser> {
	@Autowired
	private FileInfoService fileInfoService;

	public ResultVo upload(String param) throws Exception {
		JSONObject jsonMap = JSONObject.parseObject(param);
		String userId = jsonMap.getString(Global.USER_ID);
		String file = jsonMap.getString("file");
		String filename = jsonMap.getString("filename");
		try {
			Preconditions.checkArgument(StringUtils.isNotBlank(file), "上传文件不能为空");
			Preconditions.checkArgument(StringUtils.isNotBlank(filename), "上传文件名称不能为空");
		} catch (Exception e) {
			throw new ParamException(e);
		}

		FileClassifyEnum handheldId = FileClassifyEnum.HANDHELD_ID;

		FileInfo fileInfo = fileInfoService.upload4Base64(filename, file, handheldId);
		FileUser fileUser = new FileUser();
		fileUser.setType(handheldId.toString());
		fileUser.setUserId(userId);
		FileUser fineOne = dao.findOne(fileUser);
		if (null == fineOne) {
			fineOne = new FileUser();
		}
		fineOne.setDelFlag(DelFlagEnum.FALSE.getCode());
		fineOne.setFileId(fileInfo.getId());
		fineOne.setType(handheldId.toString());
		fineOne.setUserId(userId);
		fineOne.setMemo("手持身份证照片");
		saveSelective(fineOne);
		fileInfoService.saveSelective(fileInfo);
		ResultVo resultVo = new ResultVo(true);
		return resultVo;
	}
}
