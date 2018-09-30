package com.toleckk.insta.service;

import com.toleckk.insta.domain.Post;
import com.toleckk.insta.repository.PostRepository;
import com.toleckk.insta.service.dto.PostDTO;
import com.toleckk.insta.service.mapper.PostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;
/**
 * Service Implementation for managing Post.
 */
@Service
@Transactional
public class PostService {

    private final Logger log = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    public PostService(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    /**
     * Save a post.
     *
     * @param postDTO the entity to save
     * @return the persisted entity
     */
    public PostDTO save(PostDTO postDTO) {
        log.debug("Request to save Post : {}", postDTO);
        Post post = postMapper.toEntity(postDTO);
        post = postRepository.save(post);
        return postMapper.toDto(post);
    }

    /**
     * Get all the posts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<PostDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Posts");
        return postRepository.findAll(pageable)
            .map(postMapper::toDto);
    }


    /**
     * Get one post by id.
     *
     * @param key the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<PostDTO> findOne(Post.Key key) {
        log.debug("Request to get Post : {}", key);
        return postRepository.findById(key)
            .map(postMapper::toDto);
    }

    /**
     * Delete the post by id.
     *
     * @param key the id of the entity
     */
    public void delete(Post.Key key) {
        log.debug("Request to delete Post : {}", key);
        postRepository.deleteById(key);
    }
}
