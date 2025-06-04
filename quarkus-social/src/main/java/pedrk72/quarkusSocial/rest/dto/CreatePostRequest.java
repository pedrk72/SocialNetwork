package pedrk72.quarkusSocial.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePostRequest {

    @NotBlank(message = "Post text is required")
    private String postText;
}
