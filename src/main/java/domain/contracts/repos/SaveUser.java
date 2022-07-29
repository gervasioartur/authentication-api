package domain.contracts.repos;

import domain.entities.ouputs.UserOutput;

public interface SaveUser {
    UserOutput save(String name, String email, String Password);
}