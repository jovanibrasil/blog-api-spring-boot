package blog.business.services;

import java.util.List;

import blog.presentation.models.Post;

public interface PostService {
	public List<Post> findAll();
	public List<Post> findLatest(int n);
	public Post findById(Long id);
	public Post create(Post post);
	public Post edit(Post post);
	public void deleteById(Long id);
	public List<Post> findPostsByUserId(Long id);
}