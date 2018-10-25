package com.vinfol.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vinfol.model.UserLoginModel;
import com.vinfol.model.UserModel;
import com.vinfol.model.enums.AccountTypeEnums;
import com.vinfol.service.UserService;
import com.vinfol.utils.DataResponse;
import com.vinfol.utils.consts.IConstants;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	protected HttpServletRequest request;

	@PostMapping("/login")
	public DataResponse<Map<String, Object>> login(@RequestBody UserLoginModel userModel, @RequestHeader String email,
			@RequestHeader String password) {
		System.out.println("body:" + userModel);
		return userService.login(userModel.getEmail(), userModel.getPassword());
	}

	@PostMapping("/logout")
	public void loginout() {
		userService.loginout();
	}

	@PostMapping("/register")
	public DataResponse<Object> register(@RequestParam(required = true) String email,
			@RequestParam(required = true) String password, @RequestParam(required = true) String code) {
		return userService.register(email, password);
	}

	@PostMapping("/user/add")
	public DataResponse<UserModel> add(@RequestBody(required = true) UserModel userModel) {
		userModel = userService.add(userModel);
		DataResponse<UserModel> dataResponse = new DataResponse<>();
		dataResponse.setObject(userModel);
		return dataResponse;
	}

	@GetMapping("/user/delete")
	public DataResponse<Object> delete(@RequestParam(required = true) String id) {
		Object userModel = userService.delete(id);
		DataResponse<Object> dataResponse = new DataResponse<>();
		dataResponse.setObject(userModel);
		return dataResponse;
	}

	@PostMapping("/user/update")
	public DataResponse<UserModel> update(@RequestBody(required = true) UserModel userModel) {
		userModel = userService.update(userModel);
		DataResponse<UserModel> dataResponse = new DataResponse<>();
		dataResponse.setObject(userModel);
		return dataResponse;
	}

	@GetMapping("/user/list")
	public DataResponse<Page<UserModel>> list(@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "20") int size,
			@RequestParam(required = false, defaultValue = "") String name) {
		DataResponse<Page<UserModel>> dataResponse = new DataResponse<>();
		System.out.println(
				"request.getAttribute(IConstants.USERMODELMSG):" + request.getAttribute(IConstants.USERMODELMSG));
		UserModel userModel = (UserModel) request.getAttribute(IConstants.USERMODELMSG);

		if (userModel != null && userModel.getAccountType() != AccountTypeEnums.ADMIN) {
			dataResponse.statusCode = IConstants.RESPONSE_STATUS_CODE_FAILED;
			dataResponse.statusMsg = "对不起，您没有这个权限";
		} else {
			Page<UserModel> userModels = userService.listPage(page, size);
			System.out.println(userModels);
			dataResponse.setObject(userModels);
		}
		return dataResponse;
	}

	@GetMapping(path = ("/test,auth/test"))
	public Object test(String userName, String names, Pageable pageable) {
		System.out.println("http://localhost:8080/test.........");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("api: test ,,,,,,auth:" + auth);
		return userName + names + pageable.getPageSize();
	}

}
