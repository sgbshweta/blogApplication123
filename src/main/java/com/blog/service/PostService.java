package com.blog.service;

import com.blog.Payload.PostDto;
import com.blog.Payload.PostResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PostService {

  public PostDto CreatePost(PostDto postDto);
  public void DeletePost(long id);

  PostDto updatePost(long postId, PostDto postDto);
  public List<PostDto> getAllPosts();
  public List<PostDto> getAllPostsWithComments();
  public List<PostDto> getPostsWhichhasComments();
  List<PostDto> getAllPosts(int pageNo, int pageSize);
  //List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
  PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);


}
