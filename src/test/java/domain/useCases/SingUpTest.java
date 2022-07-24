package domain.useCases;

import domain.contracts.gateways.Encrypter;
import domain.contracts.repos.LoadUserByEmail;
import domain.entities.ouputs.UserOutput;
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
        when(saveUserRepo.save(name, email, password)).thenReturn(new UserOutput(id, name, email));
        sut = new SingUp(loadUserByEmail, encrypter, saveUserRepo);
    }

    @Test
    @DisplayName("Should call loadByEmail with correct input")
    void singUpCallsLoadByEmailWithCorrectInput() {
        sut.singUp(name, email, password);
        verify(loadUserByEmail, Mockito.times(1)).loadByEmail(email);
    }

}
