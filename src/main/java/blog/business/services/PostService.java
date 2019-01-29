package blog.business.services;

import java.util.List;
import java.util.Optional;

import blog.enums.ListOrderType;
import blog.presentation.models.Post;

public interface PostService {
	
	public Optional<List<Post>> findAll();
	public Optional<List<Post>> findPosts(Long n);
	public Optional<List<Post>> findPostsByUser(Long n, Long userId);
	public Optional<Post> findById(Long id);
	public Optional<Post> create(Post post);
	public Optional<Post> edit(Post post);
	public void deleteById(Long id);
	
}
