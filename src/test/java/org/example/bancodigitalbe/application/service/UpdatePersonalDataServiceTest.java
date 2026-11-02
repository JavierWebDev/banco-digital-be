package org.example.bancodigitalbe.application.service;

import org.example.bancodigitalbe.domain.exceptions.InvalidPersonalDataException;
import org.example.bancodigitalbe.domain.exceptions.UserNotFoundException;
import org.example.bancodigitalbe.domain.model.User;
import org.example.bancodigitalbe.domain.port.in.UpdatePersonalDataCommand;
import org.example.bancodigitalbe.domain.port.out.LoadUserPort;
import org.example.bancodigitalbe.domain.port.out.UpdateUserPersonalDataPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdatePersonalDataServiceTest {

    @Mock
    private LoadUserPort loadUserPort;

    @Mock
    private UpdateUserPersonalDataPort updateUserPersonalDataPort;

    private UpdatePersonalDataService updatePersonalDataService;

    @BeforeEach
    void setUp() {
        updatePersonalDataService = new UpdatePersonalDataService(loadUserPort, updateUserPersonalDataPort);
    }

    private void givenStoredUser() {
        when(loadUserPort.loadByUsername("javier")).thenReturn(Optional.of(new User(
                1L, "javier", "$2a$10$hash", "Javier", "Gomez", LocalDate.of(2000, 1, 1),
                "viejo@correo.com", "3001112233", "Calle 1")));
    }

    // HU-03 escenario 1: actualizacion exitosa de datos de contacto
    @Test
    void updatesTheContactDataOfTheTitular() {
        givenStoredUser();

        updatePersonalDataService.updatePersonalData(new UpdatePersonalDataCommand(
                "javier", "nuevo@correo.com", "3104445566", "Carrera 7 # 20-30"));

        verify(updateUserPersonalDataPort)
                .updatePersonalData(1L, "nuevo@correo.com", "3104445566", "Carrera 7 # 20-30");
    }

    // los campos que no se envian conservan su valor anterior
    @Test
    void keepsThePreviousValueOfTheFieldsThatWereNotSent() {
        givenStoredUser();

        updatePersonalDataService.updatePersonalData(
                new UpdatePersonalDataCommand("javier", null, "3104445566", null));

        verify(updateUserPersonalDataPort)
                .updatePersonalData(1L, "viejo@correo.com", "3104445566", "Calle 1");
    }

    // HU-03 escenario 2: sin ningun campo no hay nada que guardar
    @Test
    void rejectsAnUpdateWithoutAnyField() {
        assertThatThrownBy(() -> updatePersonalDataService.updatePersonalData(
                new UpdatePersonalDataCommand("javier", null, null, null)))
                .isInstanceOf(InvalidPersonalDataException.class);

        verify(updateUserPersonalDataPort, never()).updatePersonalData(any(), any(), any(), any());
    }

    @Test
    void failsWhenTheAuthenticatedUsernameNoLongerExists() {
        when(loadUserPort.loadByUsername("ghost")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> updatePersonalDataService.updatePersonalData(
                new UpdatePersonalDataCommand("ghost", "a@b.com", null, null)))
                .isInstanceOf(UserNotFoundException.class);
    }

}
