package br.com.picpaychallenge.core.gateways;

import br.com.picpaychallenge.core.domain.User;
import br.com.picpaychallenge.core.exceptions.EmailOrCpfAlreadyRegistered;

public interface UserGateway {
    User findUserById(String id);
    User createUser(User user) throws EmailOrCpfAlreadyRegistered;
}
