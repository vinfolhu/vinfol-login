package com.github.vinfolhu.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vinfolhu.utils.consts.IConstants;

public class DataResponse<T> extends BaseResponse {
	public static final ObjectMapper mapper = new ObjectMapper();

	private T Object;

	private String token;

	public T getObject() {
		if (Object == null) {
			super.statusCode = IConstants.RESPONSE_STATUS_CODE_FAILED;
		}
		return Object;
	}

	public void setObject(T object) {
		Object = object;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
