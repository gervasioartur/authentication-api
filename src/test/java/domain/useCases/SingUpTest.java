package domain.useCases;

import domain.contracts.gateways.Encrypter;
import domain.contracts.gateways.TokenGenerator;
import domain.contracts.repos.LoadUserByEmail;
import domain.contracts.repos.SaveUser;
import domain.entities.ouputs.UserOutput;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SingUpTest {
    private SingUp sut;
    private String id;
    private String name;
    private String email;
    private String password;
    private LoadUserByEmail loadUserByEmailRepo;
    private Encrypter encrypter;
    private SaveUser saveUserRepo;
    private TokenGenerator tokenGenerator;

    @BeforeAll
    public void setup() {
        id = "any_id";
        name = "any_name";
        email = "any_email";
        password = "any_password";
        loadUserByEmailRepo = Mockito.mock(LoadUserByEmail.class);
        encrypter = Mockito.mock(Encrypter.class);
        saveUserRepo = Mockito.mock(SaveUser.class);
        tokenGenerator = Mockito.mock(TokenGenerator.class);
    }

    @BeforeEach
    public void init() {
        when(loadUserByEmailRepo.loadByEmail(email)).thenReturn(null);
        when(encrypter.encrypt(password)).thenReturn("any_hashed_password");
        when(saveUserRepo.save(name, email, "any_hashed_password")).thenReturn(new UserOutput(id, name, email));
        when(tokenGenerator.generate(id)).thenReturn("any_access_token");
        sut = new SingUp(loadUserByEmailRepo, encrypter, saveUserRepo, tokenGenerator);
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

    @Test
    @DisplayName("Should call save with correct input")
    void SingUpCallsSaveUserWithCorrectInput() {
        verify(saveUserRepo, Mockito.atLeastOnce()).save(name, email, "any_hashed_password");
    }

    @Test
    @DisplayName("Should call Token generator with correct input")
    void SingUpCallsTokenGeneratorWithCorrectInput() {
        verify(tokenGenerator, Mockito.atLeastOnce()).generate(id);
    }

    @Test
    @DisplayName("Should return access token on sing up success")
    void ShouldReturnAccessTokenOnSingUpSuccess() {
        String accessToken = sut.singUp(name,email,password);
        Assertions.assertEquals("any_access_token", accessToken);
    }
}
