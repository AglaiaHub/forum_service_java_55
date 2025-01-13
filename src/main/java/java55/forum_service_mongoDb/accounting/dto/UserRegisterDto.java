package java55.forum_service_mongoDb.accounting.dto;

import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
public class UserRegisterDto {
    String login;
    String password;
    String firstName;
    String lastName;
}
