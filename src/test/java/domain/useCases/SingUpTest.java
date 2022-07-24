package domain.useCases;

import domain.contracts.gateways.Encrypter;
import domain.contracts.repos.LoadUserByEmail;
import domain.entities.errors.EmailInUserError;
import domain.entities.ouputs.LoadByEmailOutput;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
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


    @BeforeAll
    public void setup() {
        id = "any_id";
        name = "any_name";
        email = "any_email";
        password = "any_password";
        loadUserByEmail = Mockito.mock(LoadUserByEmail.class);
        encrypter =  Mockito.mock(Encrypter.class);
    }

    @BeforeEach
    public void init() {
        when(loadUserByEmail.loadByEmail(email)).thenReturn(null);
        sut = new SingUp(loadUserByEmail, encrypter);
    }

    @Test
    @DisplayName("Should return email in user error if loadByEmail return data")
    void singUpReturnsUserAlreadyExists() {
        when(loadUserByEmail.loadByEmail(email)).thenReturn(new LoadByEmailOutput(id,name, email));
        Error result = sut.singUp(name, email, password);
        assertEquals(new EmailInUserError().getMessage(), result.getMessage());
    }

    @Test
    @DisplayName("Should return an encrypted password")
    void singUpReturnsEncryptedPassword(){
        when(encrypter.encrypt(password)).thenReturn("any_hashed_password");
        String hashedPassword = sut.singUp(name, email, password);
        assertEquals("any_hashed_password", hashedPassword);
    }
}
