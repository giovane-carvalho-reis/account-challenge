package br.com.giovanecarvalho.accountchallenge.service.strategy;

import br.com.giovanecarvalho.accountchallenge.dto.AccountDto;
import br.com.giovanecarvalho.accountchallenge.dto.EventRequest;
import br.com.giovanecarvalho.accountchallenge.dto.EventResponse;
import br.com.giovanecarvalho.accountchallenge.dto.TransactionType;
import br.com.giovanecarvalho.accountchallenge.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferStrategy implements TransactionStrategy {

    private final AccountRepository repository;

    public TransferStrategy(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionType type() {
        return TransactionType.TRANSFER;
    }

    @Override
    public EventResponse execute(EventRequest request) {
        BigDecimal originBalance = repository.debit(request.origin(), request.amount());
        BigDecimal destinationBalance = repository.credit(request.destination(), request.amount());

        return new EventResponse(
                new AccountDto(request.origin(), originBalance),
                new AccountDto(request.destination(), destinationBalance)
        );
    }
}
