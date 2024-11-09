package exercise.dto;

import java.util.List;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDTO {
    long id;
    String title;
    String body;
    List<CommentDTO> comments;
}
