package com.blog.service;

import com.blog.Payload.CommentDto;

import java.util.List;

public interface CommentService {
    public CommentDto createComment( long postId , CommentDto commentDto);
    public void deleteCommentById(long commentId);
    public CommentDto updateComment(long commentId, CommentDto commentDto);

    List<CommentDto> getAllComments();
    public CommentDto getCommentById(long commentId);
}
