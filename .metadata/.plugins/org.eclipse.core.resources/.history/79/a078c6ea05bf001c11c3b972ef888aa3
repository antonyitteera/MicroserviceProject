package com.zinkwork.atm.security;


import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{
	
	@Override
	public void configure(HttpSecurity http) throws Exception
	{
		http.cors().and()
			.csrf().disable().
			authorizeRequests()
			.antMatchers(HttpMethod.GET, "api/v1/retrievebalance/**").hasRole("USER")
			.antMatchers(HttpMethod.GET, "api/v1/withdraw/**").hasRole("USER")
			.antMatchers("/h2-console/**").permitAll()
			.anyRequest().authenticated();
		http.headers().frameOptions().disable();
	}

}
