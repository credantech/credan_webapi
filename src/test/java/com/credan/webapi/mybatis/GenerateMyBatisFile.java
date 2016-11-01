/** 
 * @(#)GenerateMyBatisFile.java 1.0.0 2016年5月20日 下午7:29:57  
 *  
 * Copyright © 2016 Credan.  All rights reserved.  
 */

package com.credan.webapi.mybatis;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * 
 * 
 * @author Mond
 * @version $Revision:1.0.0, $Date: 2016年5月20日 下午7:29:57 $
 */
public class GenerateMyBatisFile {
	@Test
	public  void main() {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		File configFile = new File("./src/test/java/com/credan/webapi/mybatis/generator.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		org.mybatis.generator.config.Configuration config = null;
		try {
			config = cp.parseConfiguration(configFile);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XMLParserException e) {
			e.printStackTrace();
		}
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		try {
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("生成Mybatis配置成功， 请刷新项目！");
	}
}
