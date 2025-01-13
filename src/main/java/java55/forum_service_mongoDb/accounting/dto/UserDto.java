package java55.forum_service_mongoDb.accounting.dto;

import java55.forum_service_mongoDb.accounting.model.Role;
import lombok.*;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserDto {
    String login;
    String firstName;
    String lastName;
    @Singular
    Set<String> roles;

}
