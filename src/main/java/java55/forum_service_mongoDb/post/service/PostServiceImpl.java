package java55.forum_service_mongoDb.post.service;

import java55.forum_service_mongoDb.post.dao.PostRepository;
import java55.forum_service_mongoDb.post.dto.NewPostDto;
import java55.forum_service_mongoDb.post.dto.PostDto;
import java55.forum_service_mongoDb.post.dto.exceptions.PostNotFoundEeception;
import java55.forum_service_mongoDb.post.dto.exceptions.PostNotFoundException;
import java55.forum_service_mongoDb.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    final PostRepository postRepository;
    final ModelMapper modelMapper;
    @Override
    public PostDto addNewPost(String author, NewPostDto newPostDto) {
        Post post = new Post(
                newPostDto.getTitle(),
                newPostDto.getContent(),
                author,
                newPostDto.getTags());
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);



    }


    @Override
    public PostDto findPostById(String id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        return modelMapper.map(post, PostDto.class);
    }


    @Override
    public PostDto removePost(String id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
        return modelMapper.map(post, PostDto.class);
    }


    @Override
    public PostDto updatePost(String id, NewPostDto newPostDto) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        String content = newPostDto.getContent();
        if (content != null) {
            post.setContent(content);
        }
        String title = newPostDto.getTitle();
        if (title != null) {
            post.setTitle(title);
        }
        Set<String> tags = newPostDto.getTags();
        if (tags != null) {
            tags.forEach(post::addTag);
        }
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

}
