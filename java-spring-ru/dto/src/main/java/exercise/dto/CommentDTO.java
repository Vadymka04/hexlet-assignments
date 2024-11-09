package exercise.dto;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDTO {
    long id;
    String body;
}
