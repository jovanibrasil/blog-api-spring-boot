package com.blog.repositories;

import com.blog.models.Feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly=true)
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
	
	@Query(nativeQuery=true, value="SELECT * FROM feedbacks p LEFT JOIN users u WHERE u.user_name=p.user_name ORDER BY p.feedback_date")
	Page<Feedback> findAll(Pageable pagable);
	@Query(nativeQuery=true, value="SELECT * FROM feedbacks p LEFT JOIN users u WHERE u.user_name=p.user_name "
			+ "AND u.user_name=:user_name ORDER BY p.feedback_date")
	Page<Feedback> findByUserId(@Param("user_name") String userName, Pageable pagable);	
	
}
