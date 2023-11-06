package com.blog.controller;

import com.blog.Payload.PostDto;
import com.blog.Payload.PostResponse;
import com.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

   // http://localhost:8080/api/posts/
    @PostMapping
    public ResponseEntity<PostDto> CreatePost(@RequestBody PostDto postDto) {
        PostDto dto = postService.CreatePost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    //    http://localhost:8080/api/posts/3
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeletePost(@PathVariable("id") long id){
        postService.DeletePost(id);
        return new ResponseEntity<String>("Post deleted",HttpStatus.OK );

    }
   // http://localhost:8080/api/posts/all
    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/allpostswithComments")
    public ResponseEntity<List<PostDto>> getAllPostsWithComments() {
        List<PostDto> dto = postService.getAllPostsWithComments();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @GetMapping("/postswithonlyComments")
    public ResponseEntity<List<PostDto>> getPostsWithComments() {
        List<PostDto> dto = postService.getPostsWhichhasComments();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


//    //pagination concept
//    // http://localhost:8080/api/posts?pageNo=0&pageSize=5
//    @GetMapping
//    public List<PostDto> getAllPosts(
//            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
//            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize)
//    {
//        return postService.getAllPosts(pageNo, pageSize);
//    }

    //pagination concept with sorting
    // http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=asc
//    @GetMapping
//    public List<PostDto> getAllPosts(
//            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
//            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
//            @RequestParam(value = "sortBy",defaultValue = "id", required = false) String sortBy,
//            @RequestParam(value = "sortDir",defaultValue = "asc", required = false) String sortDir)
//
//    {
//        return postService.getAllPosts(pageNo, pageSize,sortBy,sortDir);
//    }
    //http://localhost:8080/api/posts?pageNo=0&pageSize=5&sortBy=title&sortDir=asc
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc", required = false) String sortDir)

    {
        PostResponse postResponse=postService.getAllPosts(pageNo, pageSize,sortBy,sortDir);
        return new ResponseEntity<>(postResponse,HttpStatus.OK);
}}








