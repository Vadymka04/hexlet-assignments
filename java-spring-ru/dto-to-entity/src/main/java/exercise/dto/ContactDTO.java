package exercise.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Setter
@Getter
public class ContactDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
