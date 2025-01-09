package java55.forum_service_mongoDb.post.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDto {
    String id;
    String title;
    String content;
    String author;
    LocalDateTime dateCreated;
    @Singular
    Set<String> tags;
    int likes;
    @Singular
    List<CommentDto> comments;

}


//id": "61b86639905cb348d52a138d",
//    "title": "JavaEE",
//    "content": "Java is the best for backend",
//    "author": "JavaFan",
//    "dateCreated": "2021-12-14T11:39:05",
//    "tags": [
//        "Java",
//        "backend",
//        "JEE",
//        "Spring"
//    ],
//    "likes": 0,
//    "comments": []
//}