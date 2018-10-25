package com.vinfol.service.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mongodb.WriteResult;
import com.vinfol.model.UserModel;
import com.vinfol.model.enums.StatusEnums;
import com.vinfol.repository.UserRepository;
import com.vinfol.service.UserService;
import com.vinfol.utils.DataResponse;
import com.vinfol.utils.JavaWebTokenUtils;
import com.vinfol.utils.consts.IConstants;

@Service
@SuppressWarnings("rawtypes")
public class UserServiceImpl implements UserService {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RedisTemplate redisTemplate;

	@Value("${loginType}")
	String loginType;

	@Autowired
	protected HttpServletRequest request;

	@Override
	public boolean checkToken(String token) {

		String toeknRe = (String) redisTemplate.opsForValue().get(getTokenKey(token));
		if (StringUtils.isNotBlank(toeknRe) && token.equals(toeknRe)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public DataResponse<Object> register(String email, String password) {
		UserModel userModel = new UserModel();
		userModel.setEmail(email);
		userModel.setName(email);
		userModel.setPassword(password);

		DataResponse<Object> dataResponse = new DataResponse<>();

		UserModel existModel = (UserModel) userRepository.findOne(userModel.getId());
		if (existModel != null) {
			dataResponse.setStatusCode(IConstants.RESPONSE_STATUS_CODE_PART_FAILED);
			dataResponse.setObject("Email邮箱已存在");
		} else {
			userModel = userRepository.save(userModel);
			log.info("userModel:" + userModel);
			dataResponse.setObject(userModel);
		}

		return dataResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataResponse<Map<String, Object>> login(String email, String password) {

		DataResponse<Map<String, Object>> response = new DataResponse<Map<String, Object>>();

		UserModel userModel = findOneByEmail(email);
		if (userModel == null) {
			response.setStatusCode(IConstants.RESPONSE_STATUS_CODE_FAILED);
			response.setStatusMsg("登录失败，账号不存在，请检查后重新登录");
			return response;
		}

		String pass = userModel.getPassword();
		password = JavaWebTokenUtils.getMD5Value(password);
		log.info("pass:" + pass + ",password:" + password);
		if (StringUtils.isNotBlank(pass) && pass.equals(password)) {

			log.info("login success，email：" + email);

			HashMap<String, Object> map = new HashMap<>();

			map.put("id", userModel.getId());
			map.put("email", userModel.getEmail());
			map.put("name", userModel.getName());
			map.put("accountType", userModel);
			map.put("createTime", new Date());

			String token = JavaWebTokenUtils.createJavaWebToken(map);

			response.setToken(token);
			String redisKey = token;
			if (isSingleLogin()) {
				redisKey = userModel.getId();
			}
			redisTemplate.opsForValue().set(redisKey, token, 30, TimeUnit.MINUTES);

			response.setObject(map);
			response.setStatusMsg("登录成功");
			log.info("response:" + response);

		} else {
			response.setStatusCode(IConstants.RESPONSE_STATUS_CODE_FAILED);
			response.setStatusMsg("登录失败，密码输入错误。");
			log.info("login error，email：" + email);
		}
		return response;
	}

	@Override
	public UserModel findOneByName(String name) {
		log.info("findOneByName name:" + name);
		return (UserModel) userRepository.findOneByName(name);
	}

	@Override
	public UserModel findOneByEmail(String email) {
		log.info("findOneByEmail email:" + email);
		return (UserModel) userRepository.findOneByEmail(email);
	}

	@Override
	public UserModel update(UserModel userModel) {

//		Update update = new Update();
//
//		if (StringUtils.isNotEmpty(userModel.getName())) {
//			update.set("name", userModel.getName());
//		}
//
//		// update.set("status", "DELETE");
//		mongoTemplate.updateMulti(query(where("id").is(userModel.getId()).and("status").ne("DELETE")), update,
//				UserModel.class);
//
//		return (UserModel) userRepository.findOne(userModel.getId());
		return userRepository.save(userModel);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loginout() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("auth:" + auth);
		if (auth != null) {
			String token = (String) auth.getPrincipal();
			log.info("token logout:" + token);
			String redisKey = getTokenKey(token);
			redisTemplate.delete(redisKey);
		}
	}

	public String getTokenKey(String token) {
		if (isSingleLogin()) {
			Map<String, Object> userModelMap = JavaWebTokenUtils.parserJavaWebToken(token);
			UserModel userModel = userRepository.findOneByEmail((String) userModelMap.get("email"));
			System.out.println("userModel:" + userModel);
			return userModel.getId();
		}
		return token;
	}

	public boolean isSingleLogin() {
		return SINGLE_LOGIN.equals(loginType);
	}

	@Override
	public List<UserModel> list(int page, int size) {
		page = page - 1;
		Pageable pageable = new PageRequest(page < 0 ? 0 : page, size);
		Page<UserModel> data = userRepository.findAllByStatusNot(pageable, StatusEnums.DELETE);
		System.out.println(data.getTotalElements());
		System.out.println(data.getContent());
		return mongoTemplate.find(query(where("status").ne(StatusEnums.DELETE)).with(pageable), UserModel.class);
	}

	@Override
	public Page<UserModel> listPage(int page, int size) {
		page = page - 1;
		Pageable pageable = new PageRequest(page < 0 ? 0 : page, size);
		return userRepository.findAllByStatusNot(pageable, StatusEnums.DELETE);
	}

	@Override
	public UserModel add(UserModel userModel) {
		return userRepository.save(userModel);
	}

	@Override
	public Object delete(String id) {
		// TODO Auto-generated method stub
		Update update = new Update();
		update.set("status", "DELETE");
		WriteResult writeResult = mongoTemplate.updateFirst(query(where("id").is(id).and("status").ne("DELETE")),
				update, UserModel.class);
		return writeResult.isUpdateOfExisting();
	}
}
