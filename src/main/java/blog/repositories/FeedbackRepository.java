package blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import blog.models.Feedback;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
	
	@Query(nativeQuery=true, value="SELECT * FROM feedbacks p LEFT JOIN users u WHERE u.user_id=p.user_id ORDER BY p.feedback_date DESC Limit :limit")
	List<Feedback> findFeedbacks(@Param("limit") Long limit);
	
	@Query(nativeQuery=true, value="SELECT * FROM feedbacks p LEFT JOIN users u WHERE u.user_id=p.user_id AND u.user_id=:user_id ORDER BY p.feedback_date DESC Limit :limit")
	List<Feedback> findFeedbacksByUserId(@Param("user_id") Long userId, @Param("limit") Long limit);
	
}
