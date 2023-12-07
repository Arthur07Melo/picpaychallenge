package br.com.picpaychallenge.core.gateways;

import br.com.picpaychallenge.core.domain.User;

public interface UserGateway {
    User createUser(User user);
}
