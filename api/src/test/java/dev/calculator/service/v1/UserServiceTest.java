package dev.calculator.service.v1;

import dev.calculator.model.User;
import dev.calculator.repository.UserRepository;
import dev.calculator.service.v1.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Test
    public void test__signUp() {
        // given
        User user = User.builder()
                .username("foo")
                .password("original_password_123")
                .build();
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("modifiedPassword");

        var subject = UserService.builder()
                .bCryptPasswordEncoder(bCryptPasswordEncoder)
                .userRepository(userRepository)
                .build();

        // when
        subject.signUp(user);

        // then
        assertEquals(user.getPassword(), "modifiedPassword", "password should be modified");
        verify(userRepository, times(1)).save(any(User.class));
    }

}