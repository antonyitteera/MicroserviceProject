package edu.tus.authenticationservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user_details")
@Data
public class UserEntity {
	
	@Id
	@Column(name="user_id")
	private Long accountnum;
	@Column(name = "pin")
	private Integer pin;
	@Column(name="role")
	private String role;
	
}
