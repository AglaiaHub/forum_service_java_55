package java55.forum_service_mongoDb.accounting.dto;

import lombok.*;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolesDto {
    String login;

    @Singular
    Set<String> roles;
}
