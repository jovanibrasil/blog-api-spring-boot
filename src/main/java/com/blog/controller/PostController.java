package com.blog.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.blog.model.dto.PostDTO;
import com.blog.model.dto.PostInfoDTO;
import com.blog.model.dto.PostSummaryDTO;
import com.blog.model.form.PostForm;
import com.blog.services.PostService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	
	@ApiOperation(value = "Busca post por ID.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Post encontrado e retornado.", response = PostDTO.class),
		@ApiResponse(code = 404, message = "Post não encontrado.")})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{postId}")
	public PostDTO getPost(@PathVariable("postId") Long postId) {
		return postService.findPostById(postId);
	}

	@ApiOperation(value = "Busca sumários dos posts.", notes = "Sumários são objetos com menos informações."
			+ "do que o post completo. Usados na listagem dos posts.")
	@ApiResponses({@ApiResponse(code = 200, message = "Resultado da busca.", response = PostSummaryDTO.class, responseContainer = "List")})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/summaries")
	public Page<PostSummaryDTO> getPostSummaryList(
			@PageableDefault(page = 0, size = 6, direction = Direction.DESC, sort = "creationDate") Pageable pageable,
			@RequestParam(value="category", defaultValue="all") String cat) {
		return postService.findPostSummaryList(cat, pageable);
	}
	
	@ApiOperation(value = "Busca infos dos posts.", notes = "Infos são objetos o título e id do post.")
	@ApiResponses({@ApiResponse(code = 200, message = "Resultado da busca.", response = PostInfoDTO.class, responseContainer = "List")})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/info") 
	public Page<PostInfoDTO> getTopPostInfoList(
			@PageableDefault(page = 0, direction = Direction.DESC, sort = "lastUpdateDate") Pageable pageable) {
		return postService.findPostInfoList(pageable);
	}
	
	@ApiOperation(value = "Busca todos os posts por um critério.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Resultado da busca.", response = PostDTO.class ),
		@ApiResponse(code = 400, message = "Requisição inválida.")})
	@ResponseStatus(HttpStatus.OK)
	@GetMapping
	public Page<PostDTO> getPosts(@RequestParam(value = "username", defaultValue = "") String userName,
			@PageableDefault(page = 0, direction = Direction.DESC, sort = "lastUpdateDate") Pageable pageable){
		if(!userName.isEmpty() && !userName.isBlank()) {
			return postService.findPostsByUserName(userName, pageable);
		}else {
			return postService.findPosts(pageable);	
		}
	}
		
	@ApiOperation("Cria um post.")
	@ApiResponses({@ApiResponse(code = 200, message = "Post criado com sucesso.", response = PostDTO.class)})
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(consumes = { "multipart/form-data" })
	public PostDTO createPost(
			@RequestPart(name = "post") @Valid @NotNull PostForm postForm, 
			@RequestPart(name = "banner") @NotNull MultipartFile banner,
			UriComponentsBuilder uriBuilder) {
		
		log.info("Creating post ...");
		return postService.create(postForm, banner);
	}

	@ApiOperation("Atualiza post.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Post atualizado com sucesso.", response = PostDTO.class),
		@ApiResponse(code = 404, message = "Post não encontrado.")})
	@ResponseStatus(HttpStatus.OK)
	@PutMapping(path = "/{postId}",  consumes = { "multipart/form-data" })
	public PostDTO updatePost(
			@PathVariable Long postId,
			@RequestPart(name = "post") @Valid @NotNull PostForm postForm,
			@RequestPart MultipartFile banner) {
		
		log.info("Updating post ...");
		return postService.update(postId, postForm, banner);
	}
	
	@ApiOperation(value = "Incrementa o contador de likes de um post.")
	@ApiResponses({ @ApiResponse(code = 200, message = "Incremento realizado")} )
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping("/{postId}/likes")
	public void likePost(@PathVariable Long postId) {
		postService.incrementLikes(postId);
	}
	
	@ApiOperation(value = "Remove um post.")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Post removido."),
		@ApiResponse(code = 404, message = "Post não encontrado.")})
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping(value="/{postId}")
	public void deletePost(@PathVariable("postId") Long postId){
		log.info("Deleting post ...");
		postService.deleteByPostId(postId);
	}
		
}
