package com.credan.webapi.core.dao.entity.file;

import com.credan.webapi.core.dao.entity.BasicEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileUser extends BasicEntity {

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to the database column file_user.user_id
	 *
	 * @mbg.generated Tue Nov 08 10:05:19 CST 2016
	 */
	private String userId;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to the database column file_user.file_id
	 *
	 * @mbg.generated Tue Nov 08 10:05:19 CST 2016
	 */
	private String fileId;

	/**
	 *
	 * This field was generated by MyBatis Generator. This field corresponds to the database column file_user.type
	 *
	 * @mbg.generated Tue Nov 08 10:05:19 CST 2016
	 */
	private String type;
}