package edu.tus.authenticationservice.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import edu.tus.authenticationservice.model.UserEntity;
import edu.tus.authenticationservice.repository.UserRepository;

@Component
public class MyUserDetailService implements UserDetailsService  {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<UserEntity> userOptional = userRepository.findById(Long.valueOf(username));
		System.out.println(username);
		System.out.println(userOptional.get().getAccountnum());
		if(userOptional.isPresent()) {
			UserEntity user = userOptional.get();
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
	        
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
            
            return new User(user.getAccountnum().toString(), user.getPin().toString(), authorities);
		}
		return null;
	}

}
