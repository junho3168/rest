package com.ll.rest.domain.post.post.dto;

import com.ll.rest.domain.post.post.entity.Post;
import com.ll.rest.global.jpa.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    private long id;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;
    private String title;
    private String content;

    public PostDto(Post post) {
        this.id = post.getId();
        this.createdDateTime = post.getCreatedAt();
        this.modifiedDateTime = post.getModifiedAt();
        this.title = post.getTitle();
        this.content = post.getContent();

    }
}