package br.com.picpaychallenge.core.useCases;

import br.com.picpaychallenge.core.domain.User;
import br.com.picpaychallenge.core.exceptions.EmailOrCpfAlreadyRegistered;
import br.com.picpaychallenge.core.gateways.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class CreateUserInteractorTest {
    @Mock
    private UserGateway gateway;

    @InjectMocks
    private CreateUserInteractor createUserInteractor;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeSucess() throws EmailOrCpfAlreadyRegistered{
        User userToCreate = new User("77e705f3-0809-40f6-85f7-0f208ea8b813", "Arthur",
                "999.999.999-99", "aarthur00ian@gmail.com", "admin", 50D, true);

        when(gateway.createUser(any(User.class))).thenReturn(userToCreate);

        User createdUser = createUserInteractor.execute(userToCreate);

        assertEquals(userToCreate, createdUser);
    }

    @Test
    void executeWithExistentEmailOrCpf() throws EmailOrCpfAlreadyRegistered {
        User newUser = new User("77e705f3-0809-40f6-85f7-0f208ea8b813", "Arthur",
                "999.999.999-99", "aarthur00ian@gmail.com", "admin", 50D, true);

        when(gateway.createUser(any(User.class))).thenThrow(EmailOrCpfAlreadyRegistered.class);

        assertThrows(EmailOrCpfAlreadyRegistered.class, () -> createUserInteractor.execute(newUser));
    }
}