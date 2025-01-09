package java55.forum_service_mongoDb.post.service;

import java55.forum_service_mongoDb.post.dao.PostRepository;
import java55.forum_service_mongoDb.post.dto.DatePeriodDto;
import java55.forum_service_mongoDb.post.dto.NewCommentDto;
import java55.forum_service_mongoDb.post.dto.NewPostDto;
import java55.forum_service_mongoDb.post.dto.PostDto;
import java55.forum_service_mongoDb.post.dto.exceptions.PostNotFoundException;
import java55.forum_service_mongoDb.post.model.Comment;
import java55.forum_service_mongoDb.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    final PostRepository postRepository;
    final ModelMapper modelMapper;

    @Override
    public PostDto addNewPost(String author, NewPostDto newPostDto) {
        Post post = new Post(newPostDto.getTitle(), newPostDto.getContent(), author, newPostDto.getTags());
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




    @Override
    public PostDto addComment(String id, String author, NewCommentDto newCommentDto) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        Comment comment = new Comment(author, newCommentDto.getMessage());
        post.addComment(comment);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }


    @Override
    public void addLike(String id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        post.addLike();
        postRepository.save(post);


    }


    @Override
    public Iterable<PostDto> findPostsByAuthor(String author) {
        return postRepository.findByAuthorIgnoreCase(author)
                .map(p -> modelMapper.map(p, PostDto.class))
                .toList();
    }


    @Override
    public Iterable<PostDto> findPostsByTags(List<String> tags) {
        return postRepository.findByTagsInIgnoreCase(tags)
                .map(p -> modelMapper.map(p, PostDto.class))
                .toList();
    }


    @Override
    public Iterable<PostDto> findPostsByPeriod(DatePeriodDto datePeriodDto) {
        return postRepository.findByDateCreatedBetween(datePeriodDto.getDateFrom(), datePeriodDto.getDateTo())
                .map(p -> modelMapper.map(p, PostDto.class))
                .toList();
    }


}