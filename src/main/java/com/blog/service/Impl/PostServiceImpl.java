package com.blog.service.Impl;

import com.blog.Payload.CommentDto;
import com.blog.Payload.PostDto;
import com.blog.Payload.PostResponse;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.PostNotFoundException;
import com.blog.repository.PostRepository;
import com.blog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{
private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    @Override
    public PostDto CreatePost(PostDto postDto) {
       Post post=maptoEntity(postDto);
       Post  newpost =postRepository.save(post);
       PostDto dto= maptoDto(newpost);

        return dto;
    }


    Post maptoEntity(PostDto postDto){
      Post post=new Post();
      post.setTitle(postDto.getTitle());
      post.setDescription(postDto.getDescription());
      post.setContent(postDto.getContent());
        return post;
    }
PostDto  maptoDto(Post post){
    PostDto dto=new PostDto();
    dto.setId(post.getId());
    dto.setTitle(post.getTitle());
    dto.setDescription(post.getDescription());
    dto.setContent(post.getContent());
    return dto;
}
    @Override
    public void DeletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("post with id not found  " + id)
        );
        post.getComments().clear();
        postRepository.delete(post);


    }
    //http://localhost:8080/api/posts/1
    @Override
    public PostDto updatePost(long postId, PostDto postDto) {
        // Retrieve the post by its ID
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " + postId));

        // Update the post fields with values from postDto
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        // Save the updated post
        Post updatedPost = postRepository.save(post);

        // Map the updated post to a DTO and return it
        return maptoDto(updatedPost);
}

@Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post->maptoDto(post)).collect(Collectors.toList());
    }
//    @Override
//    public List<PostDto> getAllPostsWithComments() {
//        List<Post> posts = postRepository.findAll();
//        return posts.stream().map(post->mapToDtoWithComments(post)) // Map Post entities to PostDto with comments
//                .collect(Collectors.toList());
//    }
//
//    private PostDto mapToDtoWithComments(Post post) {
//        PostDto postdto = maptoDto(post);
//        postdto.setComments(post.getComments()
//                .stream().map(dto1->mapCommentToDto(dto1))
//                .collect(Collectors.toList()));
//        return postdto;
//    }

    @Override
    public List<PostDto> getAllPostsWithComments() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post->mapToDtoWithComments(post)) // Map Post entities to PostDto with comments
                .collect(Collectors.toList());
    }

    private PostDto mapToDtoWithComments(Post post) {
        PostDto postdto = maptoDto(post);
        postdto.setComments(post.getComments()
                .stream().map(dto1->mapCommentToDto(dto1))
                .collect(Collectors.toList()));
        return postdto;
    }


    private CommentDto mapCommentToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());
        dto.setEmail(comment.getEmail());
        dto.setName(comment.getName());
        // You can map other comment fields here if needed
        return dto;
    }
    @Override
    public List<PostDto> getPostsWhichhasComments() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().filter(post -> !post.getComments().isEmpty()) // Filter out posts without comments
                .map(post->mapToDtoWithComments(post)) // Map Post entities to PostDto with comments
                .collect(Collectors.toList());
    }


        @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNo, pageSize);
         Page<Post> content = postRepository.findAll(pageable);
     List<Post> posts = content.getContent();
        return posts.stream().map(post->maptoDto(post)).collect(Collectors.toList());
    }


//    @Override
//    public List<PostDto> getAllPosts(int pageNo, int pageSize,String sortBy, String sortDir) {
//         Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
//                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//        PageRequest pageable = PageRequest.of(pageNo, pageSize,sort);
//         Page<Post> content = postRepository.findAll(pageable);
//     List<Post> posts = content.getContent();
//        return posts.stream().map(post->maptoDto(post)).collect(Collectors.toList());
//    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
              Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        PageRequest pageable = PageRequest.of(pageNo, pageSize);
         Page<Post> content = postRepository.findAll(pageable);
     List<Post> posts = content.getContent();
         List<PostDto> postDto = posts.stream().map(m -> maptoDto(m)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDto);
        postResponse.setPageNo(content.getNumber());
        postResponse.setPageSize(content.getSize());
        postResponse.setTotalPages(content.getTotalPages());
        postResponse.setTotalElements((int) content.getTotalElements());
        postResponse.setLast(content.isLast());
        return postResponse;
    }



}