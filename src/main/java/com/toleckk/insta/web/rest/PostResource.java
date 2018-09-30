package com.toleckk.insta.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.toleckk.insta.domain.Post;
import com.toleckk.insta.service.PostService;
import com.toleckk.insta.web.rest.errors.BadRequestAlertException;
import com.toleckk.insta.web.rest.util.HeaderUtil;
import com.toleckk.insta.web.rest.util.PaginationUtil;
import com.toleckk.insta.service.dto.PostDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Post.
 */
@RestController
@RequestMapping("/api")
public class PostResource {

    private final Logger log = LoggerFactory.getLogger(PostResource.class);

    private static final String ENTITY_NAME = "post";

    private final PostService postService;

    public PostResource(PostService postService) {
        this.postService = postService;
    }

    /**
     * POST  /posts : Create a new post.
     *
     * @param postDTO the postDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new postDTO, or with status 400 (Bad Request) if the post has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/posts")
    @Timed
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) throws URISyntaxException {
        log.debug("REST request to save Post : {}", postDTO);
        if (postDTO.getId() != null) {
            throw new BadRequestAlertException("A new post cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PostDTO result = postService.save(postDTO);
        return ResponseEntity.created(new URI("/api/posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /posts : Updates an existing post.
     *
     * @param postDTO the postDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated postDTO,
     * or with status 400 (Bad Request) if the postDTO is not valid,
     * or with status 500 (Internal Server Error) if the postDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/posts")
    @Timed
    public ResponseEntity<PostDTO> updatePost(@RequestBody PostDTO postDTO) throws URISyntaxException {
        log.debug("REST request to update Post : {}", postDTO);
        if (postDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PostDTO result = postService.save(postDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, postDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /posts : get all the posts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of posts in body
     */
    @GetMapping("/posts")
    @Timed
    public ResponseEntity<List<PostDTO>> getAllPosts(Pageable pageable) {
        log.debug("REST request to get a page of Posts");
        Page<PostDTO> page = postService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/posts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /posts/:key : get the "key" post.
     *
     * @param key the key of the postDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the postDTO, or with status 404 (Not Found)
     */
    @GetMapping("/posts/{key}")
    @Timed
    public ResponseEntity<PostDTO> getPost(@PathVariable Post.Key key) {
        log.debug("REST request to get Post : {}", key);
        Optional<PostDTO> postDTO = postService.findOne(key);
        return ResponseUtil.wrapOrNotFound(postDTO);
    }

    /**
     * DELETE  /posts/:key : delete the "key" post.
     *
     * @param key the key of the postDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/posts/{key}")
    @Timed
    public ResponseEntity<Void> deletePost(@PathVariable Post.Key key) {
        log.debug("REST request to delete Post : {}", key);
        postService.delete(key);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, key.toString())).build();
    }
}
