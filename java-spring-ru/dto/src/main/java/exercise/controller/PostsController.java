package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;

    @GetMapping
    public List<PostDTO> getAllPosts() {
        var postRepoData = postRepository.findAll();
        return postRepoData.stream().map(this::getPostDTO).toList();
    }

    @GetMapping(path = "/{id}")
    public PostDTO getById(@PathVariable long id) {
        var postRepoData = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        return getPostDTO(postRepoData);
    }


    private PostDTO getPostDTO(Post i) {
        var commentRepo = commentRepository.findByPostId(i.getId());
        return new PostDTO(i.getId(), i.getTitle(), i.getBody(), commentRepo.stream().map(c -> new CommentDTO(c.getId(), c.getBody())).toList());
    }
}
