package java55.forum_service_mongoDb.post.service;

import java55.forum_service_mongoDb.post.dto.DatePeriodDto;
import java55.forum_service_mongoDb.post.dto.NewPostDto;
import java55.forum_service_mongoDb.post.dto.PostDto;

import java.util.Set;

public interface PostService {
    PostDto addNewPost (String author, NewPostDto newPostDto);

    PostDto findPostById(String id);

//    PostDto removePostById(String id);

    PostDto removePost(String id);

    PostDto updatePost(String id, NewPostDto newPostDto);

    PostDto addComment(String id, String author);


    boolean addLike(String id);

    PostDto findPostByAuthor(String author);

    PostDto findPostsByTags(Set<String> tags);

    PostDto findPostsByPeriod(DatePeriodDto datePeriodDto);
}
