package com.github.vinfolhu.auth;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.github.vinfolhu.model.UserModel;
import com.github.vinfolhu.repository.UserRepository;
import com.github.vinfolhu.service.UserService;
import com.github.vinfolhu.utils.JavaWebTokenUtils;
import com.github.vinfolhu.utils.consts.IConstants;

@Component
public class AuthTokenProvider implements AuthenticationProvider {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected HttpServletRequest request;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		logger.info("======= authenticate" + authentication);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			return new UsernamePasswordAuthenticationToken(auth.getPrincipal(), null, new ArrayList<>());
		}
		String token = (String) authentication.getPrincipal();
		if (StringUtils.isNotBlank(token)) {

			Map<String, Object> userModelMap = JavaWebTokenUtils.parserJavaWebToken(token);
			if (userModelMap != null) {

				UserModel model = userRepository.findOneByEmail((String) userModelMap.get("email"));
				System.out.println("model:" + model);
				request.setAttribute(IConstants.USERMODELMSG, model);

				logger.info("======= userMap" + userModelMap);

				logger.debug("======= Authenticated successfully.");

				if (StringUtils.isNotBlank(model.getId()) && !userService.checkToken(token)) {
					throw new CredentialsExpiredException("Access Token is expired. Please login again.");
				}
			} else {
				throw new CredentialsExpiredException("Access Token is expired. Please login again.");
			}

		} else {
			throw new BadCredentialsException("Invalid token String.");
		}

		return new UsernamePasswordAuthenticationToken(token, null, new ArrayList<>());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		logger.info("======= supports");
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
