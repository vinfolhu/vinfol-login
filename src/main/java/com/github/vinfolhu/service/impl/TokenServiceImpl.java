package com.github.vinfolhu.service.impl;

import com.github.vinfolhu.model.UserModel;
import com.github.vinfolhu.repository.RedisHandle;
import com.github.vinfolhu.service.TokenService;
import com.github.vinfolhu.service.UserService;
import com.github.vinfolhu.utils.DataResponse;
import com.github.vinfolhu.utils.JavaWebTokenUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class TokenServiceImpl implements TokenService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RedisHandle redis;

	@Autowired
	UserService userService;

	public boolean checkToken(String token) {
		String toeknRe = (String) redis.get(token);
		if (StringUtils.isNotBlank(toeknRe) && token.equals(toeknRe)) {
			return true;
		} else {
			return false;
		}
	}

	public DataResponse<String> addToken(String username, String password) {

		DataResponse<String> response = new DataResponse<String>();

		UserModel userModel = null;// userService.findOneByName(username);
		if (userModel == null) {
			return response;
		}

		String pass = userModel.getPassword();

		if (StringUtils.isNotBlank(pass) && pass.equals(password)) {

			logger.info("login success" + username);

			HashMap<String, Object> map = new HashMap<>();
			map.put("id", userModel.getId());
			map.put("name", userModel.getName());
			map.put("password", userModel.getPassword());

			String token = JavaWebTokenUtils.createJavaWebToken(map);

			redis.setAndHourceTime(userModel.getId(), token, 2);

			response.setObject(token);

		} else {

			logger.info("login error");

		}
		return response;
	}

}
