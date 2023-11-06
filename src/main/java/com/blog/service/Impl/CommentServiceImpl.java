package com.blog.service.Impl;

import com.blog.Payload.CommentDto;
import com.blog.entity.Comment;
import com.blog.entity.Post;
import com.blog.exception.CommentNotFoundException;
import com.blog.exception.PostNotFoundException;
import com.blog.repository.CommentRepository;
import com.blog.repository.PostRepository;
import com.blog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment( long postId,CommentDto commentDto) {
        // Check if the post with the given postId exists
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post with ID " + postId + " not found."));

        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);

        Comment savedComment = commentRepository.save(comment);
        return mapToDto(savedComment);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());

        return comment;
    }

    private CommentDto mapToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setEmail(comment.getEmail());
        commentDto.setName(comment.getName());

        return commentDto;
    }
    //http://localhost:8080/api/posts/comments/1
    @Override
    public void deleteCommentById(long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("comment not found with"+commentId));

        commentRepository.delete(comment);
    }

    //http://localhost:8080/api/posts/comments/3
    @Override
    public CommentDto updateComment(long commentId, CommentDto commentDto) {
        // Retrieve the comment by its ID
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with ID: " + commentId));

        // Update the comment fields with values from commentDto
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());

        // Save the updated comment
        Comment updatedComment = commentRepository.save(comment);

        // Map the updated comment to a DTO and return it
        return mapToDto(updatedComment);
    }

    @Override
    public List<CommentDto> getAllComments() {
            List<Comment> comments = commentRepository.findAll();
            return comments.stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        }
    @Override
    public CommentDto getCommentById(long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Comment not found with ID: " + commentId));
        return mapToDto(comment);
    }

}
