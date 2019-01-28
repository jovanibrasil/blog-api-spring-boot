package blog.business.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import blog.enums.ListOrderType;
import blog.persistence.repositories.PostRepository;
import blog.presentation.models.Post;

@Service
@Primary
public class PostServiceJpaImpl implements PostService {

	@Autowired
	private PostRepository postRepo;
	
	@Override
	public Optional<List<Post>> findAll() {
		return Optional.of(this.postRepo.findAll());
	}

	@Override
	public Optional<List<Post>> findPosts(ListOrderType orderType, Long quantity) {
		if(orderType == ListOrderType.ASC || orderType == ListOrderType.ASC){
			return Optional.of(this.postRepo.findPosts(orderType, quantity));
		}else {
			return Optional.empty();
		}
	}
	
	@Override
	public Optional<List<Post>> findPostsByUser(ListOrderType orderType, Long quantity, Long userId) {
		if(orderType == ListOrderType.ASC || orderType == ListOrderType.ASC){
			return Optional.of(this.postRepo.findPostsByUserId(orderType, quantity, userId));
		}else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Post> findById(Long id) {
		return this.postRepo.findById(id);
	}

	@Override
	public Optional<Post> create(Post post) {
		post.setLastUpdateDate(new Date());
		return Optional.of(this.postRepo.save(post));
	}

	@Override
	public Optional<Post> edit(Post post) {
		return Optional.of(this.postRepo.save(post));
	}

	@Override
	public void deleteById(Long id) {
		this.postRepo.deleteById(id);
	}

}
