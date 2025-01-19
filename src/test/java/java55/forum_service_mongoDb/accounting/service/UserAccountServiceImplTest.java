package java55.forum_service_mongoDb.accounting.service;

import java55.forum_service_mongoDb.accounting.dao.UserAccountRepository;
import java55.forum_service_mongoDb.accounting.dto.RolesDto;
import java55.forum_service_mongoDb.accounting.dto.UserDto;
import java55.forum_service_mongoDb.accounting.dto.UserEditDto;
import java55.forum_service_mongoDb.accounting.dto.UserRegisterDto;
import java55.forum_service_mongoDb.accounting.dto.exception.UserExistException;
import java55.forum_service_mongoDb.accounting.dto.exception.UserNotFoundException;
import java55.forum_service_mongoDb.accounting.model.Role;
import java55.forum_service_mongoDb.accounting.model.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserAccountServiceImplTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserAccountServiceImpl userAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_UserAlreadyExists_ThrowsUserExistException() {
        // Arrange
        UserRegisterDto userRegisterDto = new UserRegisterDto("testUser", "1234","Ivan", "Ivanov");
        when(userAccountRepository.existsById("testUser")).thenReturn(true);

        // Act & Assert
        assertThrows(UserExistException.class, () -> userAccountService.register(userRegisterDto));

        // Verify
        verify(userAccountRepository, times(1)).existsById("testUser");
        verifyNoMoreInteractions(userAccountRepository);
    }

    @Test
    void register_NewUser_SuccessfullyRegistersUser() {
        // Arrange
        UserRegisterDto userRegisterDto = new UserRegisterDto("testUser", "1234","Ivan", "Ivanov");


        UserAccount userAccount = new UserAccount("testUser", "1234","Ivan", "Ivanov");

        UserDto userDto = new UserDto("testUser","Ivan", "Ivanov", new HashSet<>());

        when(userAccountRepository.existsById("testUser")).thenReturn(false);
        when(modelMapper.map(userRegisterDto, UserAccount.class)).thenReturn(userAccount);
        when(modelMapper.map(userAccount, UserDto.class)).thenReturn(userDto);

        // Act
        UserDto result = userAccountService.register(userRegisterDto);

        // Assert
        assertNotNull(result);
        assertEquals("testUser", result.getLogin());

        // Verify
        verify(userAccountRepository, times(1)).existsById("testUser");
        verify(modelMapper, times(1)).map(userRegisterDto, UserAccount.class);
        verify(userAccountRepository, times(1)).save(userAccount);
        verify(modelMapper, times(1)).map(userAccount, UserDto.class);
    }

    @Test
    void testGetUser_Success() {
        // Arrange
        String login = "testUser";
        UserAccount userAccount = new UserAccount(login, "1234","Ivan", "Ivanov");
        UserDto userDto = new UserDto();
        when(userAccountRepository.findById(login)).thenReturn(Optional.of(userAccount));
        when(modelMapper.map(userAccount, UserDto.class)).thenReturn(userDto);

        // Act
        UserDto result = userAccountService.getUser(login);

        // Assert
        assertNotNull(result);
        verify(userAccountRepository).findById(login);
        verify(modelMapper).map(userAccount, UserDto.class);
    }

    @Test
    void testGetUser_UserNotFound() {
        // Arrange
        String login = "nonExistingUser";
        when(userAccountRepository.findById(login)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userAccountService.getUser(login));
        verify(userAccountRepository).findById(login);
    }

    @Test
    void testRemoveUser_Success() {
        // Arrange
        String login = "testUser";
        UserAccount userAccount = new UserAccount(login, "1234","Ivan", "Ivanov");
        UserDto userDto = new UserDto();

        // Mock behavior
        when(userAccountRepository.findById(login)).thenReturn(Optional.of(userAccount));
        when(modelMapper.map(userAccount, UserDto.class)).thenReturn(userDto);

        // Act
        UserDto result = userAccountService.removeUser(login);

        // Assert
        assertNotNull(result);
        verify(userAccountRepository).findById(login);
        verify(userAccountRepository).delete(userAccount);
        verify(modelMapper).map(userAccount, UserDto.class);
    }

    @Test
    void testRemoveUser_UserNotFound() {
        // Arrange
        String login = "nonExistingUser";
        when(userAccountRepository.findById(login)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userAccountService.removeUser(login));
        verify(userAccountRepository).findById(login);
        verify(userAccountRepository, never()).delete(any());
    }

    @Test
    void testUpdateUser_Success() {
        // Arrange
        String login = "testUser";
        UserEditDto userEditDto = new UserEditDto("Ivan", "Ivanov");
        UserAccount userAccount = new UserAccount(login, "1234","Ivan", "Ivanov");
        UserDto userDto = new UserDto();

        // Mock behavior
        when(userAccountRepository.findById(login)).thenReturn(Optional.of(userAccount));
        when(modelMapper.map(userAccount, UserDto.class)).thenReturn(userDto);

        // Act
        UserDto result = userAccountService.updateUser(login, userEditDto);

        // Assert
        assertNotNull(result);
        assertEquals("Ivan", userAccount.getFirstName());
        assertEquals("Ivanov", userAccount.getLastName());
        verify(userAccountRepository).findById(login);
        verify(userAccountRepository).save(userAccount);
        verify(modelMapper).map(userAccount, UserDto.class);
    }

    @Test
    void testUpdateUser_UserNotFound() {
        // Arrange
        String login = "nonExistingUser";
        UserEditDto userEditDto = new UserEditDto();
        when(userAccountRepository.findById(login)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userAccountService.updateUser(login, userEditDto));
        verify(userAccountRepository).findById(login);
        verify(userAccountRepository, never()).save(any());
    }

    @Test
    void testChangeRolesList_AddRole() {
//todo
    }


    @Test
    void testChangeRolesList_RemoveRole() {
//todo
    }

    @Test
    void testChangeRolesList_UserNotFound() {
        // Arrange
        String login = "nonExistingUser";
        String role = "ADMIN";
        when(userAccountRepository.findById(login)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userAccountService.changeRolesList(login, role, true));
        verify(userAccountRepository).findById(login);
        verify(userAccountRepository, never()).save(any());
//        verify(userAccount, never()).addRole(role);
    }

}
