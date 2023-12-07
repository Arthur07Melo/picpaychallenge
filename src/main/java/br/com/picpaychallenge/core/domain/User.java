package br.com.picpaychallenge.core.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class User {
    private String fullName;

    //Must be unique
    private String cpf;

    //Must be unique
    private String email;

    private String password;

    @Setter
    private Double balance;

    private Boolean isShopKeeper;
}