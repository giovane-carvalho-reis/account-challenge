package br.com.giovanecarvalho.accountchallenge.service.strategy;

import br.com.giovanecarvalho.accountchallenge.dto.AccountDto;
import br.com.giovanecarvalho.accountchallenge.dto.EventRequest;
import br.com.giovanecarvalho.accountchallenge.dto.EventResponse;
import br.com.giovanecarvalho.accountchallenge.dto.TransactionType;
import br.com.giovanecarvalho.accountchallenge.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class DepositStrategy implements TransactionStrategy {

    private final AccountRepository repository;

    public DepositStrategy(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionType type() {
        return TransactionType.DEPOSIT;
    }

    @Override
    public EventResponse execute(EventRequest request) {
        BigDecimal balance = repository.credit(request.destination(), request.amount());
        return new EventResponse(null, new AccountDto(request.destination(), balance));
    }
}
