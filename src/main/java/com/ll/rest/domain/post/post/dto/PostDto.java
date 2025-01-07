package com.ll.rest.domain.post.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ll.rest.domain.post.post.entity.Post;
import com.ll.rest.global.jpa.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
public class PostDto {
    private long id;
    @JsonProperty("createdDatetime")
    private LocalDateTime createDate;
    @JsonProperty("modifiedDatetime")
    private LocalDateTime modifyDate;
    private String title;
    private String content;

    public PostDto(Post post) {
        this.id = post.getId();
        this.createDate = post.getCreatedAt();
        this.modifyDate = post.getModifiedAt();
        this.title = post.getTitle();
        this.content = post.getContent();

    }

}