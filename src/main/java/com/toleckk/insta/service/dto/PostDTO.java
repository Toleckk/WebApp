package com.toleckk.insta.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Post entity.
 */
public class PostDTO implements Serializable {

    private Long id;

    private String text;

    private String resource;

    private Long ownerId;

    private String ownerLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long userId) {
        this.ownerId = userId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String userLogin) {
        this.ownerLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PostDTO postDTO = (PostDTO) o;
        if (postDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), postDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", resource='" + getResource() + "'" +
            ", owner=" + getOwnerId() +
            ", owner='" + getOwnerLogin() + "'" +
            "}";
    }
}
