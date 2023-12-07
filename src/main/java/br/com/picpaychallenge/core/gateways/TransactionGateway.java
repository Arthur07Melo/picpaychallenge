package br.com.picpaychallenge.core.gateways;

import br.com.picpaychallenge.core.domain.Transaction;

public interface TransactionGateway {
    Transaction createTransaction(Transaction transaction);
}
