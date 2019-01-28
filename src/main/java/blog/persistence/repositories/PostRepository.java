package blog.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import blog.enums.ListOrderType;
import blog.presentation.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	
	@Query(nativeQuery=true, value="SELECT p FROM Post p INNET JOIN user ORDER BY p.lastUpdateDate ?orderType Limit 0, ?quantity")
	List<Post> findPosts(@Param("orderType") ListOrderType orderType, @Param("quantity") Long quantity);
	
	@Query(nativeQuery=true, value="SELECT p FROM Post p INNER JOIN user WHERE user.userId=?userId ORDER BY p.lastUpdateDate ?orderType Limit 0, ?quantity")
	List<Post> findPostsByUserId(@Param("orderType") ListOrderType orderType, @Param("quantity") Long quantity, @Param("userId") Long userId);
	
}

