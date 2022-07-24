package domain.contracts.repos;

import domain.entities.ouputs.UserOutput;

public interface LoadUserByEmail {
    UserOutput loadByEmail(String email);
}