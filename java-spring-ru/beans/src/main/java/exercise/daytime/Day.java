package exercise.daytime;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Controller;

@Controller
public class Day implements Daytime {
    private String name = "day";

    public String getName() {
        return name;
    }

    @PostConstruct
    public void init() {
        System.out.println("Day bean was created");
    }
}
