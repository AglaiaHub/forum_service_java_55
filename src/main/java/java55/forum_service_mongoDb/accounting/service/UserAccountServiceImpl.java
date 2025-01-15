package java55.forum_service_mongoDb.accounting.service;

import java55.forum_service_mongoDb.accounting.dao.UserAccountRepository;
import java55.forum_service_mongoDb.accounting.dto.RolesDto;
import java55.forum_service_mongoDb.accounting.dto.UserDto;
import java55.forum_service_mongoDb.accounting.dto.UserEditDto;
import java55.forum_service_mongoDb.accounting.dto.UserRegisterDto;
import java55.forum_service_mongoDb.accounting.model.Role;
import java55.forum_service_mongoDb.accounting.model.UserAccount;
import java55.forum_service_mongoDb.post.dto.exceptions.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    final UserAccountRepository userAccountRepository;
    final ModelMapper modelMapper;

    @Override
    public UserDto register(UserRegisterDto userRegisterDto) {
        UserAccount userAccount = new UserAccount(userRegisterDto.getLogin(), userRegisterDto.getPassword(), userRegisterDto.getFirstName(), userRegisterDto.getLastName());
        userAccount = userAccountRepository.save(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto removeUser(String login) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(PostNotFoundException::new);
        userAccountRepository.delete(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto getUser(String login) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(PostNotFoundException::new);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto updateUser(String login, UserEditDto userEditDto) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(PostNotFoundException::new);
        if(userEditDto.getFirstName()!=null) userAccount.setFirstName(userEditDto.getFirstName());
        if(userEditDto.getLastName()!=null) userAccount.setLastName(userEditDto.getLastName());
        userAccount = userAccountRepository.save(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public RolesDto changeRolesList(String login, String role, boolean isAddRole) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(PostNotFoundException::new);


        if(isAddRole){
            userAccount.addRole(role);
        } else {
            if(!role.equalsIgnoreCase("user")){
                userAccount.removeRole(role);
            }
        }

        userAccount = userAccountRepository.save(userAccount);
        return modelMapper.map(userAccount, RolesDto.class);
    }

    @Override
    public void changePassword(String name, String newPassword) {
        // mistake? why by name, not by login?

    }
}
