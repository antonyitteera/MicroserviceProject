package edu.tus.authenticationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.tus.authenticationservice.model.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long>{

}
