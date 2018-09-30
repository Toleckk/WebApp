package com.toleckk.insta.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Post.
 */
@Entity
@IdClass(Post.Key.class)
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Post implements Serializable {
    @Embeddable
    public static class Key implements Serializable{
        private Long id;
        private User owner;

        public Key(){}

        public Key(Long id, User owner){
            this.id = id;
            this.owner = owner;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public User getOwner() {
            return owner;
        }

        public void setOwner(User owner) {
            this.owner = owner;
        }
    }

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "jhi_resource")
    private String resource;

    @Id
    @ManyToOne
    @JsonIgnoreProperties("")
    private User owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public Post text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResource() {
        return resource;
    }

    public Post resource(String resource) {
        this.resource = resource;
        return this;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public User getOwner() {
        return owner;
    }

    public Post owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        if (post.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Post{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", resource='" + getResource() + "'" +
            "}";
    }
}
