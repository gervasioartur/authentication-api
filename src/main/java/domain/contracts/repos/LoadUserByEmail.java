package domain.contracts.repos;

import domain.entities.ouputs.LoadByEmailOutput;

public interface LoadUserByEmail {
    LoadByEmailOutput loadByEmail(String email);
}