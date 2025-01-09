package java55.forum_service_mongoDb.post.controller;

import java55.forum_service_mongoDb.post.dto.DatePeriodDto;
import java55.forum_service_mongoDb.post.dto.NewCommentDto;
import java55.forum_service_mongoDb.post.dto.NewPostDto;
import java55.forum_service_mongoDb.post.dto.PostDto;
import java55.forum_service_mongoDb.post.model.Post;
import java55.forum_service_mongoDb.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class PostController {

    private final PostService postService;

    @PostMapping("/post/{author}")
    public PostDto addNewPost(@PathVariable String author, @RequestBody NewPostDto newPostDto) {
        return postService.addNewPost(author, newPostDto);
    }

    @GetMapping("/post/{id}")
    public PostDto findPostById(@PathVariable String id) {
        return postService.findPostById(id);
    }

    @DeleteMapping("/post/{id}")
    public PostDto removePost(@PathVariable String id) {
        return postService.removePost(id);
    }

    @PutMapping("/post/{id}")
    public PostDto updatePost(@PathVariable String id, @RequestBody NewPostDto newPostDto) {
        return postService.updatePost(id, newPostDto);
    }

    @PutMapping("/post/{id}/comment/{author}")
    public PostDto addComment(@PathVariable String id, @PathVariable String author,
                              @RequestBody NewCommentDto newCommentDto) {
        return postService.addComment(id, author, newCommentDto);
    }

    @PutMapping("/post/{id}/like")
    public void addLike(@PathVariable String id) {
        postService.addLike(id);

    }

    @GetMapping("/posts/author/{author}")
    public Iterable<PostDto> findPostByAuthor(@PathVariable String author) {
        return postService.findPostsByAuthor(author);
    }

    @PostMapping("/posts/tags")
    public Iterable<PostDto> findPostsByTags(@RequestBody List<String> tags) {
        return postService.findPostsByTags(tags);
    }

    @PostMapping("/posts/period")
    public Iterable<PostDto> findPostsByPeriod(@RequestBody DatePeriodDto datePeriodDto) {
        return postService.findPostsByPeriod(datePeriodDto);
    }

}