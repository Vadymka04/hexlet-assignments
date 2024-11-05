package exercise.model;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "task")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    String title;
    String description;
    @CreatedDate
    LocalDate createdAt;
    @LastModifiedDate
    LocalDate updatedAt ;

}
