package com.blog.Payload;

import lombok.Data;

import java.util.List;
@Data
public class PostDto {
    private long id;
    private String title;
    private String description;
    private String content;
    private List<CommentDto> comments;


}

