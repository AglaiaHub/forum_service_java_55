package java55.forum_service_mongoDb.post.service;

import java55.forum_service_mongoDb.post.dto.DatePeriodDto;
import java55.forum_service_mongoDb.post.dto.NewCommentDto;
import java55.forum_service_mongoDb.post.dto.NewPostDto;
import java55.forum_service_mongoDb.post.dto.PostDto;

import java.util.List;
import java.util.Set;

public interface PostService {

    PostDto addNewPost(String author, NewPostDto newPostDto);

    PostDto findPostById(String id);

    PostDto removePost(String id);

    PostDto updatePost(String id, NewPostDto newPostDto);

    PostDto addComment(String id, String author, NewCommentDto newCommentDto);

    void addLike(String id);

    Iterable<PostDto> findPostsByAuthor(String author);

    Iterable<PostDto> findPostsByTags(List<String> tags);

    Iterable<PostDto> findPostsByPeriod(DatePeriodDto datePeriodDto);


}