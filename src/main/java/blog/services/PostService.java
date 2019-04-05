package blog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import blog.enums.ListOrderType;
import blog.models.Post;

public interface PostService {
	
	public Optional<List<Post>> findAll();
	public Optional<Page<Post>> findPosts(Long limit);
	public Optional<Page<Post>> findPostsByCategory(String category, Long limit);
	public Optional<Page<Post>> findPostsByUser(String userName, Long n);
	public Optional<Post> findById(Long id);
	public Optional<Post> create(Post post);
	public Optional<Post> edit(Post post);
	public Optional<Post> deleteById(Long id);
	
}
