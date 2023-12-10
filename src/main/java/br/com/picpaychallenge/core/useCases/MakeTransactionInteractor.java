package br.com.picpaychallenge.core.useCases;

import br.com.picpaychallenge.core.domain.Transaction;
import br.com.picpaychallenge.core.domain.User;
import br.com.picpaychallenge.core.exceptions.NotSuficientBalanceException;
import br.com.picpaychallenge.core.exceptions.ShopKeeperCantMakeTransaction;
import br.com.picpaychallenge.core.exceptions.TransactionNotAuthorized;
import br.com.picpaychallenge.core.external.ValidateTransaction;
import br.com.picpaychallenge.core.gateways.TransactionGateway;
import br.com.picpaychallenge.core.port.IdGenerator;

public class MakeTransactionInteractor {

    private final ValidateTransaction validateTransaction;
    private final TransactionGateway transactionGateway;
    private final IdGenerator idGenerator;

    public MakeTransactionInteractor(TransactionGateway transactionGateway, ValidateTransaction validateTransaction, IdGenerator idGenerator){
        this.transactionGateway = transactionGateway;
        this.validateTransaction = validateTransaction;
        this.idGenerator = idGenerator;
    }

    public Transaction execute(User sender, User receiver, Double amount) throws Exception {
        Double senderBalance = sender.getBalance();

        if(senderBalance < amount){
            throw new NotSuficientBalanceException();
        }

        if(!validateTransaction.execute()) {
            throw new TransactionNotAuthorized();
        }

        if(sender.getIsShopKeeper()){
            throw new ShopKeeperCantMakeTransaction();
        }

        sender.setBalance(senderBalance - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        Transaction transaction = new Transaction(idGenerator.generate(), sender, receiver, amount);
        return transactionGateway.createTransaction(transaction);
    }
}
