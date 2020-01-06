package com.blog.repositories;

import com.blog.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly=true)
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
	@Query(nativeQuery=true, value="SELECT * FROM feedbacks p LEFT JOIN users u WHERE u.user_name=p.user_name ORDER BY p.feedback_date DESC Limit :limit")
	List<Feedback> findFeedbacks(@Param("limit") Long limit);
	@Query(nativeQuery=true, value="SELECT * FROM feedbacks p LEFT JOIN users u WHERE u.user_name=p.user_name "
			+ "AND u.user_name=:user_name ORDER BY p.feedback_date DESC Limit :limit")
	List<Feedback> findFeedbacksByUserId(@Param("user_name") String userName, @Param("limit") Long limit);	
}
