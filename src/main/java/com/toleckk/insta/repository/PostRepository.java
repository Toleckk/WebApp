package com.toleckk.insta.repository;

import com.toleckk.insta.domain.Post;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Post entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PostRepository extends JpaRepository<Post, Post.Key> {

    @Query("select post from Post post where post.owner.login = ?#{principal.username}")
    List<Post> findByOwnerIsCurrentUser();

}
