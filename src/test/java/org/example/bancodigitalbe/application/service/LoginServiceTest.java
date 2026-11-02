package org.example.bancodigitalbe.application.service;

import org.example.bancodigitalbe.domain.exceptions.UserNotFoundException;
import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.out.LoadUserPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private LoadUserPort loadUserPort;

    private User existingUser() {
        return new User(1L, "javier", "$2a$10$hash", "Javier", "Gomez", LocalDate.of(2000, 1, 1), null, null, null);
    }

    @Test
    void returnsTheUserWhenTheUsernameExists() {
        LoginService loginService = new LoginService(loadUserPort);
        when(loadUserPort.loadByUsername("javier")).thenReturn(Optional.of(existingUser()));

        User user = loginService.login("javier");

        assertThat(user.getUserId()).isEqualTo(1L);
        assertThat(user.getUser()).isEqualTo("javier");
    }

    @Test
    void failsWhenTheUsernameDoesNotExist() {
        LoginService loginService = new LoginService(loadUserPort);
        when(loadUserPort.loadByUsername("ghost")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loginService.login("ghost"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("ghost");
    }

}
