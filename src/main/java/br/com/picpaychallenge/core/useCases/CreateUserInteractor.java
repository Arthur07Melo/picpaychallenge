package br.com.picpaychallenge.core.useCases;

import br.com.picpaychallenge.core.domain.User;
import br.com.picpaychallenge.core.exceptions.EmailOrCpfAlreadyRegistered;
import br.com.picpaychallenge.core.gateways.UserGateway;

public class CreateUserInteractor {
    private final UserGateway gateway;

    public CreateUserInteractor(UserGateway gateway){
        this.gateway = gateway;
    }

    public User execute(User user) throws EmailOrCpfAlreadyRegistered {
        return gateway.createUser(user);
    }
}
