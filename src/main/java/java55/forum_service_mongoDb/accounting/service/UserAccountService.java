package java55.forum_service_mongoDb.accounting.service;

import java55.forum_service_mongoDb.accounting.dto.RolesDto;
import java55.forum_service_mongoDb.accounting.dto.UserDto;
import java55.forum_service_mongoDb.accounting.dto.UserEditDto;
import java55.forum_service_mongoDb.accounting.dto.UserRegisterDto;

import java.security.Principal;

public interface UserAccountService {
    UserDto register(UserRegisterDto userRegisterDto);
    UserDto removeUser(String login);

    UserDto getUser(String login);

    UserDto updateUser(String login, UserEditDto userEditDto);

    RolesDto changeRolesList(String login, String role, boolean isAddRole);

    void changePassword(String login, String newPassword);
}
