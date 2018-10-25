package com.vinfol.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vinfol.utils.consts.IConstants;

public class BaseResponse {
	public static final ObjectMapper mapper = new ObjectMapper();
	/**
	 * status code default 200
	 */
	public String statusCode = IConstants.RESPONSE_STATUS_CODE_SUCCESS;
	/**
	 * status message default success
	 */
	public String statusMsg = IConstants.RESPONSE_STATUS_CODE_SUCCESS_MSG;

	public BaseResponse() {
		statusCode = IConstants.RESPONSE_STATUS_CODE_SUCCESS;
		statusMsg = IConstants.RESPONSE_STATUS_CODE_SUCCESS_MSG;
	}

	public BaseResponse(String statusCode, String statusMsg) {
		super();
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
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
