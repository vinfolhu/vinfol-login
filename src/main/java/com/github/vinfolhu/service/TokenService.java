package com.github.vinfolhu.service;

import com.github.vinfolhu.utils.DataResponse;


@Deprecated
public interface TokenService {
	
	boolean checkToken(String token);
	
	DataResponse<String> addToken(String username, String password);
}

