/**
 * @(#) FileInfoService.java
 * 
 * Copyright (c) 2016, Credan(上海)-版权所有
 * 
 */
package com.credan.webapi.core.service.file;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.credan.webapi.comm.enums.ConstantEnums.FileClassifyEnum;
import com.credan.webapi.comm.util.DateHelper;
import com.credan.webapi.comm.util.FileHelper;
import com.credan.webapi.comm.util.UUIDUtils;
import com.credan.webapi.core.dao.entity.file.FileInfo;
import com.credan.webapi.core.dao.mapper.file.FileInfoDao;
import com.credan.webapi.core.service.BasicService;
import com.google.common.io.Files;

/**
 * 文件相关Service
 * 
 * @author Mond
 * @version 1.0.0, $Date: 2016年9月22日 下午7:37:08 $
 */
@Service
public class FileInfoService extends BasicService<FileInfoDao, FileInfo> {

	@Value("${system.private.file.path}")
	private String privateFilePath;
	
	@Override
	protected int saveSelective(FileInfo t) {
		return super.saveSelective(t);
	}



	/**
	 * Base64格式上传
	 * 
	 * @param filename
	 * @param file
	 * @param fileClassifyEnum
	 * @return
	 * @throws IOException
	 */
	public FileInfo upload4Base64(String filename, String file, FileClassifyEnum fileClassifyEnum) throws IOException {
		Date currentTime = DateHelper.getCurrentTime();
		String uploadPath = "/" + new SimpleDateFormat("yyyy/MM/dd/HH/mm/ss").format(currentTime);
		/** 如果路径不存在,则创建 */
		FileHelper.mkDir(privateFilePath + uploadPath);
		String ext = filename.substring(filename.lastIndexOf("."), filename.length());
		String uploadFileName = "pri_" + DateHelper.formatDate(currentTime, "yyyyMMddHHmmss") + UUIDUtils.getUUID()
				+ ext;
		/* 构建上传文件名 */
		String fullUploadFileName = privateFilePath + uploadPath + File.separator + uploadFileName;
		byte[] buffer = Base64.getMimeDecoder().decode(file);
		Files.write(buffer, new File(fullUploadFileName));
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFilename(uploadFileName);
		fileInfo.setFilePath(fullUploadFileName);
		fileInfo.setFileType(fileClassifyEnum.toString());
		fileInfo.setOldFilename(filename);
		return fileInfo;
	}

}
