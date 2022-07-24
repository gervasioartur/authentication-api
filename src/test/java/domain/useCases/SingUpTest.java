package domain.useCases;

import domain.contracts.gateways.Encrypter;
import domain.contracts.repos.LoadUserByEmail;
import domain.entities.ouputs.UserOutput;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(Lifecycle.PER_CLASS)
public class SingUpTest {
    private SingUp sut;
    private String id;
    private String name;
    private String email;
    private String password;
    private LoadUserByEmail loadUserByEmailRepo;
    private Encrypter encrypter;
    private SaveUserRepo saveUserRepo;

    @BeforeAll
    public void setup() {
        id = "any_id";
        name = "any_name";
        email = "any_email";
        password = "any_password";
        loadUserByEmailRepo = Mockito.mock(LoadUserByEmail.class);
        encrypter = Mockito.mock(Encrypter.class);
    }

    @BeforeEach
    public void init() {
        when(loadUserByEmailRepo.loadByEmail(email)).thenReturn(null);
        sut = new SingUp(loadUserByEmailRepo, encrypter, saveUserRepo);
        sut.singUp(name, email, password);
    }

    @Test
    @DisplayName("Should call loadByEmail with correct input")
    void singUpCallsLoadByEmailWithCorrectInput() {
        verify(loadUserByEmailRepo, Mockito.atLeastOnce()).loadByEmail(email);
    }

    @Test
    @DisplayName("Should return email in use Error if email is already taken")
    void singUpReturnEmailInUseError() {
        when(loadUserByEmailRepo.loadByEmail(email)).thenReturn(new UserOutput(id, name, email));
        Error result = sut.singUp(name, email, password);
        Assertions.assertEquals("This email is already in use, please user other email or try to login.", result.getMessage());
    }

    @Test
    @DisplayName("Should call encrypt with correct input")
    void SingUpCallsEncryptWithCorrectInput() {
        verify(encrypter, Mockito.atLeastOnce()).encrypt(password);
    }

}
