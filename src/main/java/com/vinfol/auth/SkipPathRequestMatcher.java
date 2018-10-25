package com.vinfol.auth;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SkipPathRequestMatcher implements RequestMatcher {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	private OrRequestMatcher matchers;
	private OrRequestMatcher processingMatcher;

	public SkipPathRequestMatcher(List<String> pathsToSkip, List<String> processingPath) {
		logger.info("SkipPathRequestMatcher" + pathsToSkip + ",processingPath:" + processingPath);
		List<RequestMatcher> m = pathsToSkip.stream().map(path -> new AntPathRequestMatcher(path))
				.collect(Collectors.toList());
		matchers = new OrRequestMatcher(m);

		List<RequestMatcher> m1 = processingPath.stream().map(path -> new AntPathRequestMatcher(path))
				.collect(Collectors.toList());
		processingMatcher = new OrRequestMatcher(m1);
		// processingMatcher = new AntPathRequestMatcher(processingPath);
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		boolean matched = true;
		if (matchers.matches(request)) {
			matched = false;
		} else {
			matched = processingMatcher.matches(request);
		}
		logger.info("matches, path:" + request.getServletPath() + ",matched:" + matched);
		return matched;
	}
}
