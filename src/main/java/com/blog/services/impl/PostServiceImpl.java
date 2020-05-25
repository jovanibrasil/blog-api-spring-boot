package com.blog.services.impl;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.exception.NotFoundException;
import com.blog.model.Image;
import com.blog.model.Post;
import com.blog.model.User;
import com.blog.model.dto.PostDTO;
import com.blog.model.dto.PostInfoDTO;
import com.blog.model.dto.PostSummaryDTO;
import com.blog.model.form.PostForm;
import com.blog.model.mapper.PostInfoMapper;
import com.blog.model.mapper.PostMapper;
import com.blog.model.mapper.SummaryMapper;
import com.blog.repositories.PostRepository;
import com.blog.services.ImageService;
import com.blog.services.PostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final ImageService imageService;

	private final PostInfoMapper postInfoMapper;
	private final PostMapper postMapper;
	private final SummaryMapper summaryMapper;

	/**
	 * Returns a list with size "length" that contains posts ordered by the parameter "order". No user is specified.
	 * 
	 */
	@Override
	public Page<PostDTO> findPosts(Pageable page) {
		return postRepository.findAll(page).map(postMapper::postToPostDto);
	}

	@Override
	public Page<PostDTO> findPostsByCategory(String category, Pageable page) {
		return postRepository.findByCategory(category, page).map(postMapper::postToPostDto);
	}

	/**
	 * Returns a list of n latest posts of a specified user.
	 *
	 * @param userId is the user identification. 
	 * 
	 */
	@Override
	public Page<PostDTO> findPostsByUserName(String userName, Pageable page) {
		return postRepository.findByUserName(userName, page).map(postMapper::postToPostDto);
	}

	/**
	 * Returns a post given a post id.
	 * 
	 * @param id is the post id. 
	 * 
	 */
	@Override
	public PostDTO findPostById(Long id) {
		return postRepository.findById(id)
				.map(postMapper::postToPostDto)
				.orElseThrow(() -> new NotFoundException("error.post.find"));
	}

	/**
	 * 
	 * Deletes a specific post.
	 * 
	 * @param id is the id of the post that will be deleted.
	 * @return
	 */
	@Transactional
	@Override
	public void deleteByPostId(Long postId) {
		postRepository.findById(postId).ifPresentOrElse(
				postRepository::delete,
				() -> { throw new NotFoundException("error.post.find"); }
		);
	}
	
	/**
	 * Creates a post with the received information.
	 * @param banner 
	 */
	@Transactional
	@Override
	public PostDTO create(PostForm postForm, MultipartFile banner) {
		Post post = postMapper.postFormToPost(postForm);
		post.setBanner(new Image(banner));
		
		log.info("Saving post (body + banner)");
		Image bannerImage = imageService.saveImage(post.getBanner());
		post.setBanner(bannerImage);
		
		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user =  new User(userName);
		post.setAuthor(user);
		
		post.setLastUpdateDate(LocalDateTime.now());
		post.setCreationDate(LocalDateTime.now());
		post = postRepository.save(post);
		return postMapper.postToPostDto(post);
	}

	/**
	 * Returns a list of post summaries. A post summary is an object with basic information
	 * about a specific post, like id, title and tags.  
	 * 
	 * @param page
	 * @param cat
	 * @return
	 */
	@Override
	public Page<PostSummaryDTO> findPostSummaryList(String cat, Pageable pageable) {
		if(cat.equals("all")) {
			return postRepository.findAll(pageable).map(summaryMapper::postToSummaryDto);
		}
		return postRepository.findByCategory(cat, pageable)
				.map(summaryMapper::postToSummaryDto);
	}

	/**
	 * Returns a list of PostInfo objects. A PostInfo object contains id and title of
	 * an post. The size of the list id determined by TOP_POSTS_LIST_SIZE.
	 * 
	 * @return
	 */
	@Override
	public Page<PostInfoDTO> findPostInfoList(Pageable pageable) {
		return postRepository.findAll(pageable).map(postInfoMapper::postToPostInfoDto);
	}

	/**
	 * Updates a specified post.
	 * 
	 */
	@Transactional
	@Override
	public PostDTO update(Long postId, PostForm postForm, MultipartFile banner) {
		Post receivedPost = postMapper.postFormToPost(postForm);
		Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("error.post.find"));
		post.setTitle(receivedPost.getTitle());
		post.setBody(receivedPost.getBody());
		post.setSummary(receivedPost.getSummary());
		post.setLastUpdateDate(LocalDateTime.now());
		post.setTags(receivedPost.getTags());
		post.getBanner().setContent(receivedPost.getBanner().getMultipartBanner());
		return postMapper.postToPostDto(post);
	}

}
