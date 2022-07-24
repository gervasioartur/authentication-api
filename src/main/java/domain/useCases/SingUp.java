package domain.useCases;

import domain.contracts.gateways.Encrypter;
import domain.contracts.repos.LoadUserByEmail;
import domain.entities.errors.EmailInUserError;
import domain.entities.ouputs.LoadByEmailOutput;

public class SingUp {
    private LoadUserByEmail loadByEmailRepo;
    private Encrypter encrypter;

    public SingUp(LoadUserByEmail loadByEmailRepo, Encrypter encrypter) {
        this.loadByEmailRepo = loadByEmailRepo;
        this.encrypter =  encrypter;
    }

    @SuppressWarnings("unchecked")
    <T> T singUp(String name, String email, String password) {
        LoadByEmailOutput result = this.loadByEmailRepo.loadByEmail(email);
        if (result == null) {
            return (T) this.encrypter.encrypt(password);
        }
        return (T) new EmailInUserError();
    }

}