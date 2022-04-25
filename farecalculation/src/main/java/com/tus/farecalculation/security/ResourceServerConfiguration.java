package com.tus.farecalculation.security;


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
		http.cors().and()
			.csrf().disable().
			authorizeRequests()
			//.antMatchers(HttpMethod.POST, "/api/v1/passenger").hasAuthority("admin")
			.antMatchers("/h2-console/**").permitAll()
			.antMatchers(AUTH_WHITELIST).permitAll()
			.antMatchers(HttpMethod.POST,"/api/v2/passenger").permitAll() //to control the tapping
			.anyRequest().authenticated();
		http.headers().frameOptions().disable();
	}

}
