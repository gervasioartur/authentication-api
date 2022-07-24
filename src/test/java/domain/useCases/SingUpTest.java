package domain.useCases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;

import domain.contracts.repos.LoadUserByEmail;
import domain.entities.errors.EmailInUserError;
import domain.entities.ouputs.LoadByEmailOutput;

@TestInstance(Lifecycle.PER_CLASS)
public class SingUpTest {
	private LoadUserByEmail loadUserByEmail;
	private Singup sut;
	
	private String name;
	private String email;
	private String password;
	
	@BeforeAll
	public void setup() {
		name =  "any_name";
		email = "any_email";
		password  = "any_password";
		loadUserByEmail = Mockito.mock(LoadUserByEmail.class);
		sut = new Singup(loadUserByEmail);
	}
	
	@BeforeEach
	public void init() {
		sut.singup(name, email, password);
	}
	
	@Test
	@DisplayName("Should return email in user error if loadByEmail return data")
	void singReturnsUserAlreadyExists() {
		when(loadUserByEmail.loadByEmail(email)).thenReturn(new LoadByEmailOutput(name, email));
		Error result = sut.singup(name, email, password);
		assertEquals(new EmailInUserError().getMessage(), result.getMessage());
	}
}
