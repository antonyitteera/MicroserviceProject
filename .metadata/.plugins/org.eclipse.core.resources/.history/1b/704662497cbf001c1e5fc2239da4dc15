package edu.tus.authenticationservice.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.tus.authenticationservice.model.UserEntity;
import edu.tus.authenticationservice.repository.UserRepository;
import edu.tus.authenticationservice.security.JwtUtil;
import edu.tus.authenticationservice.view.LoginForm;
import edu.tus.authenticationservice.view.LoginOutput;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController{

	@Autowired
	private AuthenticationManager authManager;
    @Autowired
    private JwtUtil jwtUtility;
    @Autowired
    private UserRepository userRepository;

    /*
     * LogIn Service
     * -------------
     * The controller for the user log in functionality. User to login with the credentials.Validation of the credentials happens here.
     * JWT authentication mechanism is implemented here.
     * 
     * Return Type (Login_Output_to_Ui) - includes customer name, customer id and JWT token. 
     */
   @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginForm loginForm)
    {
    	try
    	{
    		LoginOutput loginOutput = new LoginOutput();
	    	authenticate(loginForm);
	    	UserEntity user=userRepository.findById(Long.valueOf(loginForm.getUsername())).get();
	    	UserDetails ud = new User(user.getAccountnum().toString(), user.getPin().toString(), new ArrayList<>());
	    	String jwttoken=jwtUtility.GenerateToken(ud);
	    	loginOutput.setJwttoken(jwttoken);
	    	return ResponseEntity.status(200).body(loginOutput);
	    }
    	catch(Exception e)
    	{
    		return ResponseEntity.status(500).body(e.getMessage());
    	}
    }
    public void authenticate(LoginForm loginForm) throws Exception
    {
    	try
    	{
    		authManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(),loginForm.getPassword()));
    	}
    	catch(BadCredentialsException ex) {
    		throw new Exception("Incorrect user name or password");
    	}
    }
}