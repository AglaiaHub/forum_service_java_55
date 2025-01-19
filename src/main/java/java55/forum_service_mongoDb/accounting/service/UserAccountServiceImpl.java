package java55.forum_service_mongoDb.accounting.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java55.forum_service_mongoDb.accounting.dao.UserAccountRepository;
import java55.forum_service_mongoDb.accounting.dto.RolesDto;
import java55.forum_service_mongoDb.accounting.dto.UserDto;
import java55.forum_service_mongoDb.accounting.dto.UserEditDto;
import java55.forum_service_mongoDb.accounting.dto.UserRegisterDto;
import java55.forum_service_mongoDb.accounting.dto.exception.UserExistException;
import java55.forum_service_mongoDb.accounting.dto.exception.UserNotFoundException;
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
        if (userAccountRepository.existsById(userRegisterDto.getLogin())) {
            throw new UserExistException();
        }
        UserAccount userAccount = modelMapper.map(userRegisterDto, UserAccount.class);
        BCrypt.Hasher hashpw = BCrypt.with(BCrypt.Version.VERSION_2A);
        String password = hashpw.hashToString(8, userAccount.getPassword().toCharArray());
        userAccount.setPassword(password);
        userAccountRepository.save(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }


    @Override
    public UserDto getUser(String login) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
        return modelMapper.map(userAccount, UserDto.class);
    }


    @Override
    public UserDto removeUser(String login) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
        userAccountRepository.delete(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }


    @Override
    public UserDto updateUser(String login, UserEditDto userEditDto) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
        if (userEditDto.getFirstName() != null) {
            userAccount.setFirstName(userEditDto.getFirstName());
        }
        if (userEditDto.getLastName() != null) {
            userAccount.setLastName(userEditDto.getLastName());
        }
        userAccountRepository.save(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }


    @Override
    public RolesDto changeRolesList(String login, String role, boolean isAddRole) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
        boolean res;
        if (isAddRole) {
            res = userAccount.addRole(role);
        } else {
            res = userAccount.removeRole(role);
        }
        if(res) {
            userAccountRepository.save(userAccount);
        }
        return modelMapper.map(userAccount, RolesDto.class);
    }


    @Override
    public void changePassword(String login, String newPassword) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(UserNotFoundException::new);
        userAccount.setPassword(newPassword);
        userAccountRepository.save(userAccount);

    }


}
