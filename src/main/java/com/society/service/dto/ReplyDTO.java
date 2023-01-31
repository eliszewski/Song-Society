package com.society.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.society.domain.Reply} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReplyDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime date;

    @Lob
    private String content;

    private UserDTO user;

    private PostDTO post;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public PostDTO getPost() {
        return post;
    }

    public void setPost(PostDTO post) {
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReplyDTO)) {
            return false;
        }

        ReplyDTO replyDTO = (ReplyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, replyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReplyDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", content='" + getContent() + "'" +
            ", user=" + getUser() +
            ", post=" + getPost() +
            "}";
    }
}
