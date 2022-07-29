package domain.useCases;

import domain.contracts.gateways.Encrypter;
import domain.contracts.gateways.TokenGenerator;
import domain.contracts.repos.LoadUserByEmail;
import domain.contracts.repos.SaveUser;
import domain.entities.errors.EmailInUserError;
import domain.entities.ouputs.UserOutput;

public class SingUp {
    private final LoadUserByEmail loadByEmailRepo;
    private final Encrypter encrypter;
    private final SaveUser saveUserRepo;
    private final TokenGenerator tokenGenerator;

    public SingUp(LoadUserByEmail loadByEmailRepo, Encrypter encrypter, SaveUser saveUserRepo, TokenGenerator tokenGenerator) {
        this.loadByEmailRepo = loadByEmailRepo;
        this.encrypter = encrypter;
        this.saveUserRepo = saveUserRepo;
        this.tokenGenerator = tokenGenerator;
    }

    @SuppressWarnings("unchecked")
    <T> T singUp(String name, String email, String password) {
        UserOutput result = this.loadByEmailRepo.loadByEmail(email);
        if (result == null) {
            String hashedPassword = this.encrypter.encrypt(password);
            UserOutput user = this.saveUserRepo.save(name, email, hashedPassword);
            System.out.println(user.getId());
            if (user != null) {
                this.tokenGenerator.generate(user.getId());
            }
        }
        return (T) new EmailInUserError();
    }
}