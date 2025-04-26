package pedrk72.quarkusSocial.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class CreatePostRequest {
    @NotBlank(message = "Post text is required")
    private String postText;

    @NotBlank(message = "Date time is required")
    private LocalDateTime dateTime;


}
