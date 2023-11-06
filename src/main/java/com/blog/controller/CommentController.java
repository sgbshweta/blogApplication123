package com.blog.controller;



import com.blog.Payload.CommentDto;
import com.blog.Payload.PostDto;
import com.blog.service.CommentService;
import com.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class CommentController {
    private  CommentService commentService;
    private PostService postService;
    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;}

//http://localhost:8080/api/posts/1/comments
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDto> createCommentForPost(@RequestBody CommentDto commentDto,
                                                           @PathVariable long postId) {
        CommentDto createdComment = commentService.createComment( postId,commentDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }
    //http://localhost:8080/api/posts/comments/11
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") long commentId) {
        commentService.deleteCommentById(commentId);
        return new ResponseEntity<>("Comment deleted", HttpStatus.OK);
    }

    //http://localhost:8080/api/posts/comments/11
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("commentId") long commentId, @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = commentService.updateComment(commentId, commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }
    //http://localhost:8080/api/posts/comments
    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> getAllComments() {
        List<CommentDto> comments = commentService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    @GetMapping("/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") long commentId) {
        CommentDto comment = commentService.getCommentById(commentId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
}

