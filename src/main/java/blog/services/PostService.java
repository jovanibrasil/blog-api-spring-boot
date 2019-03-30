package blog.services;

import java.util.List;
import java.util.Optional;

import blog.enums.ListOrderType;
import blog.models.Post;

public interface PostService {
	
	public Optional<List<Post>> findAll();
	public Optional<List<Post>> findPosts(Long limit);
	public Optional<List<Post>> findPostsByCategory(String category, Long limit);
	public Optional<List<Post>> findPostsByUser(Long n, Long userId);
	public Optional<Post> findById(Long id);
	public Optional<Post> create(Post post);
	public Optional<Post> edit(Post post);
	public Optional<Post> deleteById(Long id);
	
}
