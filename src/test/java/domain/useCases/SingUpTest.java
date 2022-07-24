package domain.useCases;

import domain.contracts.gateways.Encrypter;
import domain.contracts.repos.LoadUserByEmail;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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
    private LoadUserByEmail loadUserByEmail;
    private Encrypter encrypter;
    private SaveUserRepo saveUserRepo;

    @BeforeAll
    public void setup() {
        id = "any_id";
        name = "any_name";
        email = "any_email";
        password = "any_password";
        loadUserByEmail = Mockito.mock(LoadUserByEmail.class);
        encrypter = Mockito.mock(Encrypter.class);
        saveUserRepo = Mockito.mock(SaveUserRepo.class);
    }

    @BeforeEach
    public void init() {
        when(loadUserByEmail.loadByEmail(email)).thenReturn(null);
        when(encrypter.encrypt(password)).thenReturn("any_hashed_password");
        sut = new SingUp(loadUserByEmail, encrypter, saveUserRepo);
        sut.singUp(name, email, password);
    }

    @Test
    @DisplayName("Should call loadByEmail with correct input")
    void singUpCallsLoadByEmailWithCorrectInput() {
        verify(loadUserByEmail, Mockito.atLeastOnce()).loadByEmail(email);
    }

    @Test
    @DisplayName("Should call encrypt with correct input")
    void singUpCallsEncrypterWithCorrectInput() {
        verify(encrypter, Mockito.atLeastOnce()).encrypt(password);
    }

    @Test
    @DisplayName("Should call save with correct input")
    void singUpCallsSaveUserInput() {
        verify(saveUserRepo, Mockito.atLeastOnce()).save(name, email, "any_hashed_password");
    }
}
