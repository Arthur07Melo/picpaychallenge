package br.com.picpaychallenge.core.useCases;

import br.com.picpaychallenge.core.domain.Transaction;
import br.com.picpaychallenge.core.domain.User;
import br.com.picpaychallenge.core.exceptions.NotSuficientBalanceException;
import br.com.picpaychallenge.core.exceptions.TransactionNotAuthorized;
import br.com.picpaychallenge.core.external.ValidateTransaction;
import br.com.picpaychallenge.core.gateways.TransactionGateway;
import br.com.picpaychallenge.core.port.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MakeTransactionInteractorTest {
    @InjectMocks
    private MakeTransactionInteractor makeTransaction;

    @Mock
    private ValidateTransaction validateTransaction;

    @Mock
    private TransactionGateway transactionGateway;

    @Mock
    private IdGenerator idGenerator;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executeSuccess() throws Exception {
        User sender = new User("77e705f3-0809-40f6-85f7-0f208ea8b813", "Arthur", "999.999.999-99", "aarthur00ian@gmail.com", "admin", 32D, false);
        User receiver = new User("7568ec7c-2f64-4fad-8767-b56dc7b83ff8", "Samuka", "999.999.999-99", "samuka00@gmail.com", "admin", 100D, false);

        when(idGenerator.generate()).thenReturn("9d553c6f-e93f-4723-96b2-dd07a5266e75");

        Transaction transaction = new Transaction(idGenerator.generate(), sender, receiver, 20D);

        when(validateTransaction.execute()).thenReturn(true);
        when(transactionGateway.createTransaction(any(Transaction.class))).thenReturn(transaction);

        assertEquals(transaction, makeTransaction.execute(sender, receiver, 20D));
        assertEquals(12D, sender.getBalance());
        assertEquals(120D, receiver.getBalance());
    }

    @Test
    void executeNotSuficientBalance() throws Exception{
        User sender = new User("77e705f3-0809-40f6-85f7-0f208ea8b813", "Arthur", "999.999.999-99", "aarthur00ian@gmail.com", "admin", 32D, false);
        User receiver = new User("7568ec7c-2f64-4fad-8767-b56dc7b83ff8", "Samuka", "999.999.999-99", "samuka00@gmail.com", "admin", 100D, false);

        when(idGenerator.generate()).thenReturn("9d553c6f-e93f-4723-96b2-dd07a5266e75");

        Transaction transaction = new Transaction(idGenerator.generate(), sender, receiver, 33D);

        when(validateTransaction.execute()).thenReturn(true);
        when(transactionGateway.createTransaction(any(Transaction.class))).thenReturn(transaction);

        assertThrows(NotSuficientBalanceException.class, () -> makeTransaction.execute(sender, receiver, 33D));
    }

    @Test
    void executeTransactionNotAuthorized() throws Exception{
        User sender = new User("77e705f3-0809-40f6-85f7-0f208ea8b813", "Arthur", "999.999.999-99", "aarthur00ian@gmail.com", "admin", 32D, false);
        User receiver = new User("7568ec7c-2f64-4fad-8767-b56dc7b83ff8", "Samuka", "999.999.999-99", "samuka00@gmail.com", "admin", 100D, false);

        when(idGenerator.generate()).thenReturn("9d553c6f-e93f-4723-96b2-dd07a5266e75");

        Transaction transaction = new Transaction(idGenerator.generate(), sender, receiver, 20D);

        when(validateTransaction.execute()).thenReturn(false);
        when(transactionGateway.createTransaction(any(Transaction.class))).thenReturn(transaction);

        assertThrows(TransactionNotAuthorized.class, () -> makeTransaction.execute(sender, receiver, 20D));
    }
}