package exercise.controller.users;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import exercise.model.Post;
import exercise.Data;

@RequestMapping("/api")
@RestController
public class PostsController {

    private List<Post> posts = Data.getPosts();

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users/{id}/posts") // Список страниц
    public ResponseEntity<List<Post>> index(@PathVariable() int id) {
        var post = posts.stream().filter(p -> p.getUserId() == id).toList();
        return ResponseEntity.ok(post);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/users/{id}/posts") // Список страниц
    public ResponseEntity<Post> createPost(@PathVariable int id,
                                                     @RequestBody Post post) {
        if (post.getSlug() == null || post.getSlug().isBlank() ||
                post.getBody() == null || post.getBody().isBlank() ||
                post.getTitle() == null || post.getTitle().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        post.setUserId(id);
        posts.add(post); // Добавляем пост в хранилище

        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }
}
