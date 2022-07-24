package domain.useCases;

import domain.contracts.repos.LoadUserByEmail;
import domain.entities.errors.EmailInUserError;
import domain.entities.ouputs.LoadByEmailOutput;

public class Singup {
	private LoadUserByEmail loadByEmailRepo;
	
	public Singup(LoadUserByEmail loadByEmailRepo) {
		this.loadByEmailRepo =  loadByEmailRepo;
	}
	
	@SuppressWarnings("unchecked")
	<T> T singup(String name, String email, String pasword) {
		LoadByEmailOutput result =  this.loadByEmailRepo.loadByEmail(email);
		if(result !=  null) return (T) new EmailInUserError();
		return null;
	}

}