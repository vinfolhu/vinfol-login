package com.vinfol.config.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.vinfol.auth.AuthTokenFilter;
import com.vinfol.auth.SkipPathRequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebAuthConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Bean
	public AuthTokenFilter authenticationTokenFilterBean() throws Exception {
		System.out.println("authenticationTokenFilterBean");
		List<String> pathsToSkip = Arrays.asList(
				"/login", 
				"/verify/****", 
				"/register", 
				"/template/publish/list", 
				"/swagger*", 
				"/webjars/****",
				"/webjars/*/*",
				"/v2/api-docs",
				"/swagger-ui.html/*/*/*",
				"/swagger-resources/configuration/ui",
				"/webjars/springfox-swagger-ui/*/*"
				);
//		List<String> processingPath = Arrays.asList("/service/**", "/logout", "/test", "/auth*");
		List<String> processingPath = Arrays.asList("/*","/**","/*/*");
		SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, processingPath);
		AuthTokenFilter filter = new AuthTokenFilter(matcher);
		filter.setAuthenticationManager(authenticationManager);
		return filter;
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {

		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				// we don't need CSRF because our token is invulnerable
				.csrf().disable()

				// .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

				// don't create session
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				.authorizeRequests()
				// .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

				// allow anonymous resource requests
				.antMatchers("/", "/*.html", "/**/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js").permitAll()
//				.antMatchers("/login", "/logout","/register","api**","/swagger-ui*").permitAll()
//				.antMatchers("/**").authenticated();

				// add token Fileter
				.and().addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		// add cors support
		httpSecurity.cors().configurationSource(corsConfigurationSource());
		// add logout
		httpSecurity.logout().disable();
		// disable page caching
		httpSecurity.headers().cacheControl();

	}

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		return filter;
	}
}