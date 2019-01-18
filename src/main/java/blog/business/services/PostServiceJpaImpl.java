package blog.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import blog.persistence.repositories.PostRepository;
import blog.presentation.models.Post;

@Service
@Primary
public class PostServiceJpaImpl implements PostService {

	@Autowired
	private PostRepository postRepo;
	
	@Override
	public List<Post> findAll() {
		return this.postRepo.findAll();
	}

	@Override
	public List<Post> findLatest(int n) {
		return this.postRepo.findLatest5Posts(new PageRequest(0, 5));
	}

	@Override
	public Optional<Post> findById(Long id) {
		return this.postRepo.findById(id);
	}

	@Override
	public Post create(Post post) {
		return this.postRepo.save(post);
	}

	@Override
	public Post edit(Post post) {
		return this.postRepo.save(post);
	}

	@Override
	public void deleteById(Long id) {
		this.postRepo.deleteById(id);
	}

	@Override
	public List<Post> findPostsByUserId(Long userId) {
		return this.postRepo.findPostsByUserId(userId);
	}

}
