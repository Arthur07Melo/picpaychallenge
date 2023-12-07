package br.com.picpaychallenge.core.useCases;

import br.com.picpaychallenge.core.domain.Transaction;
import br.com.picpaychallenge.core.domain.User;
import br.com.picpaychallenge.core.exceptions.NotSuficientBalanceException;
import br.com.picpaychallenge.core.exceptions.TransactionNotAuthorized;
import br.com.picpaychallenge.core.external.ValidateTransaction;
import br.com.picpaychallenge.core.gateways.TransactionGateway;

public class MakeTransactionInteractor {

    private final ValidateTransaction validateTransaction;
    private final TransactionGateway transactionGateway;

    public MakeTransactionInteractor(TransactionGateway transactionGateway, ValidateTransaction validateTransaction){
        this.transactionGateway = transactionGateway;
        this.validateTransaction = validateTransaction;
    }

    public Transaction execute(User sender, User receiver, Double amount) throws Exception {
        Double senderBalance = sender.getBalance();

        if(senderBalance < amount){
            throw new NotSuficientBalanceException();
        }

        if(!validateTransaction.execute()) {
            throw new TransactionNotAuthorized();
        }

        sender.setBalance(senderBalance - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        Transaction transaction = new Transaction(sender, receiver, amount);
        return transactionGateway.createTransaction(transaction);
    }
}
