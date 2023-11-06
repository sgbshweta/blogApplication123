package com.blog.Payload;

import com.blog.entity.Post;
import lombok.Data;

@Data
public class CommentDto {
    private long id;
    private String body;
    private String email;
    private String name;



}


