package java55.forum_service_mongoDb.accounting.controller;

import java55.forum_service_mongoDb.accounting.dto.RolesDto;
import java55.forum_service_mongoDb.accounting.dto.UserDto;
import java55.forum_service_mongoDb.accounting.dto.UserEditDto;
import java55.forum_service_mongoDb.accounting.dto.UserRegisterDto;
import java55.forum_service_mongoDb.accounting.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping ("/account")
@RequiredArgsConstructor
public class UserAccountController {
    final UserAccountService userAccountService;

    @PostMapping("/register")
    public UserDto register(@RequestBody UserRegisterDto userRegisterDto){
        return userAccountService.register(userRegisterDto);
    }

    @PostMapping("/login") //TODO Don't testing by postMan
    public UserDto login(Principal principal){
        return userAccountService.getUser(principal.getName());
    }

    @GetMapping("/user/{login}")
    public UserDto getUser (@PathVariable String login){
        return userAccountService.getUser(login);
    }

    @DeleteMapping("/user/{login}")
    public UserDto removeUser (@PathVariable String login) {
        return userAccountService.removeUser(login);
    }

    @PutMapping("user/{login}")
    public UserDto updateUser(@PathVariable String login, @RequestBody UserEditDto userEditDto){
        return userAccountService.updateUser(login, userEditDto);
    }

    @PutMapping("/user/{login}/role/{role}")
    public RolesDto addRole (@PathVariable String login, @PathVariable String role){
        return userAccountService.changeRolesList(login, role, true);
    }

    @DeleteMapping("/{login}/role/{role}")
    public RolesDto removeRole (@PathVariable String login, @PathVariable String role){
        return userAccountService.changeRolesList(login, role, false);
    }

    @PutMapping("/password") //TODO Don't testing by postMan
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword (Principal principal, @RequestHeader ("X-Password") String newPassword ){
         userAccountService.changePassword(principal.getName(), newPassword);

    }





    ///register	Post	permit all
    ///login	Post	authenticated
    ///user/{login}	Delete	authenticated, owner or Administrator
    ///user/{login}	Put	authenticated, owner
    ///user/{login}/role/{role}	Put	Administrator
    ///user/{login}/role/{role}	Delete	Administrator
    ///password	Put	authenticated, owner
    ///user/{login}	Get	authenticated


}
