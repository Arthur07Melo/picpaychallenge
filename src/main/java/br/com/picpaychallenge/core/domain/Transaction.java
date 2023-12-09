package br.com.picpaychallenge.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Transaction {
    //must be unique
    private String id;

    private User sender;

    private User receiver;

    private Double amount;
}
