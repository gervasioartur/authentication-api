package domain.useCases;

import domain.contracts.gateways.Encrypter;
import domain.contracts.repos.LoadUserByEmail;
import domain.entities.errors.EmailInUserError;
import domain.entities.ouputs.UserOutput;

interface SaveUserRepo {
    UserOutput save(String name, String email, String Password);
}

public class SingUp {
    private LoadUserByEmail loadByEmailRepo;
    private Encrypter encrypter;
    private SaveUserRepo saveUserRepo;

    public SingUp(LoadUserByEmail loadByEmailRepo, Encrypter encrypter, SaveUserRepo saveUserRepo) {
        this.loadByEmailRepo = loadByEmailRepo;
        this.encrypter = encrypter;
        this.saveUserRepo = saveUserRepo;
    }

    <T> T singUp(String name, String email, String password) {
        UserOutput result = this.loadByEmailRepo.loadByEmail(email);
        if (result == null) {
            String hashedPassword = this.encrypter.encrypt(password);
            this.saveUserRepo.save(name, email, hashedPassword);
            return null;
        }
        return (T) new EmailInUserError();
    }

}