package blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	// The implementation is provided by JPA.
}
