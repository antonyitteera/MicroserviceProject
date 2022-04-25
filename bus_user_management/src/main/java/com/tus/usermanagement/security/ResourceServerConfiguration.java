package com.tus.usermanagement.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{
	
	private static final String[] AUTH_WHITELIST = {
	        "/swagger-resources/**",
	        "/swagger-ui.html",
	        "/v2/api-docs",
	        "/webjars/**",
	        "/swagger-ui/**",
	        "/v3/api-docs"
	};
	
	@Override
	public void configure(HttpSecurity http) throws Exception
	{
//		http.cors().and()
//			.csrf().disable()
//			.authorizeRequests().anyRequest().permitAll();
//		http.headers().frameOptions().disable();
		
		http.cors().and()
		.csrf().disable().
		authorizeRequests()
		.antMatchers(HttpMethod.GET, "/api/v1/users").hasAuthority("admin")
		.antMatchers("/h2-console/**").permitAll()
		//.antMatchers("/swagger-ui/**").permitAll()
		.antMatchers(AUTH_WHITELIST).permitAll()
		.antMatchers(HttpMethod.POST,"/api/v1/user/**").permitAll() //to insert data to database
		.antMatchers(HttpMethod.POST,"/api/v1/tap").permitAll()
		.antMatchers(HttpMethod.PUT,"/api/v1/user/*/deduct").permitAll()
		.anyRequest().authenticated();
	http.headers().frameOptions().disable();
	
	}
	

}
