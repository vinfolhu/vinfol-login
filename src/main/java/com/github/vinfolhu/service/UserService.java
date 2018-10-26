package com.github.vinfolhu.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.github.vinfolhu.model.UserModel;
import com.github.vinfolhu.utils.DataResponse;

public interface UserService {

	public static final String SINGLE_LOGIN = "S";
	UserModel findOneByName(String name);

	DataResponse<Object> register(String email, String password);

	DataResponse<Map<String, Object>> login(String email, String password);

	UserModel update(UserModel userModel);

	UserModel findOneByEmail(String email);

	void loginout();

	List<UserModel> list(int page, int size);

	UserModel add(UserModel userModel);

	Object delete(String id);

	Page<UserModel> listPage(int page, int size);

	boolean checkToken(String token);

}
