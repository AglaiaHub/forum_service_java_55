package java55.forum_service_mongoDb.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewPostDto {

    String title;
    String content;
    Set<String> tags;
}
