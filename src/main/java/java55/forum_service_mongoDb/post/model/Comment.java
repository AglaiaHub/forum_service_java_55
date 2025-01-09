package java55.forum_service_mongoDb.post.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"user", "dateCreated"})
public class Comment {

    @Setter
    String user;
    @Setter
    String message;
    LocalDateTime dateCreated;
    int likes;

    public Comment(String user, String message) {
        this.user = user;
        this.message = message;
    }

    public void addLike(){

    }
}
