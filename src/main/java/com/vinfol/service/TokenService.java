package com.vinfol.service;

import com.vinfol.utils.DataResponse;


@Deprecated
public interface TokenService {
	
	boolean checkToken(String token);
	
	DataResponse<String> addToken(String username, String password);
}

