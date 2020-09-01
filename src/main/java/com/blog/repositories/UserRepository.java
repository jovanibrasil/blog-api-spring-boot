package com.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.blog.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	@Transactional(readOnly=true)
	@Query("SELECT u FROM User u WHERE u.userName=:userName")
	Optional<User> findByName(@Param("userName") String userName);
	
}
