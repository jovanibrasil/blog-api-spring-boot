package blog.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import blog.enums.ProfileTypeEnum;
import blog.presentation.models.Post;
import blog.presentation.models.User;

@Service
public class PostServiceStubImpl implements PostService {

	private List<Post> posts = new ArrayList<Post>() {{
        add(new Post(1L, "First Post", "<p>Line #1.</p><p>Line #2</p>", null));
        add(new Post(2L, "Second Post",
        "Second post content:<ul><li>line 1</li><li>line 2</li></p>",
        new User(10L, "pesho10", "Peter Ivanov", ProfileTypeEnum.ROLE_USER)));
        add(new Post(3L, "Post #3", "<p>The post number 3 nice</p>",
        new User(10L, "merry", null, ProfileTypeEnum.ROLE_USER)));
        add(new Post(4L, "Forth Post", "<p>Not interesting post</p>", null));
        add(new Post(5L, "Post Number 5", "<p>Just posting</p>", null));
        add(new Post(6L, "Sixth Post", "<p>Another interesting post</p>", null));
    }};
	
	@Override
	public List<Post> findAll() {
		return this.posts;
	}

	@Override
	public List<Post> findLatest(int n) {
		return this.posts.stream() 
				.sorted((a, b) -> b.getLastUpdateDate().compareTo(a.getLastUpdateDate()))
				.limit(n)
				.collect(Collectors.toList());
	}

	@Override
	public Post findById(Long id) {
		return this.posts.stream()
				.filter(p -> Objects.equals(p.getPostId(), id))
				.findFirst()
				.orElse(null);
	}

	@Override
	public Post create(Post post) {
		// search the max value and increment the value
		long id = this.posts.stream().mapToLong(p -> p.getPostId()).max().getAsLong()+1;
		post.setPostId(id); // set this value to the post
		this.posts.add(post);
		return post;
	}

	@Override
	public Post edit(Post post) {
		for (int i = 0; i < posts.size(); i++) {
			if(Objects.equals(this.posts.get(i).getPostId(), post.getPostId())) {
				this.posts.set(i, post);
				return post;
			}
		}
		throw new RuntimeException("Post not found "+ post.getPostId());
	}

	@Override
	public void deleteById(Long id) {
		for (int i = 0; i < posts.size(); i++) {
			if(Objects.equals(this.posts.get(i).getPostId(), id)) {
				this.posts.remove(i);
			}
		}
		throw new RuntimeException("Post not found "+ id);
	}

	@Override
	public List<Post> findPostsByUserId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
