package pedrk72.quarkusSocial.rest.dto;

import lombok.Data;
import pedrk72.quarkusSocial.domain.model.Post;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private String text;
    private LocalDateTime dateTime;

    public static PostResponse fromEntity (Post post){
        PostResponse postConverted = new PostResponse();

        postConverted.setText(post.getPostText());
        postConverted.setDateTime(post.getDateTime());

        return postConverted;
    }
}
