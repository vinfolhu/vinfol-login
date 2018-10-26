package com.github.vinfolhu.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vinfolhu.model.enums.StatusEnums;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

public class BaseObject {

	public static final ObjectMapper mapper = new ObjectMapper();

	@Id
	public String id; // 主键id uuid

	public StatusEnums status;// 数据状态：有效，无效，删除

	@CreatedDate
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date createTime; // 创建时间

	@LastModifiedDate
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date updateTime; // 更新时间

	public BaseObject() {
		this.setUpdateTime(new Date());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public StatusEnums getStatus() {
		return status;
	}

	public void setStatus(StatusEnums status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
