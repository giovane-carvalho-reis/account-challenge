package br.com.giovanecarvalho.accountchallenge.service.strategy;

import br.com.giovanecarvalho.accountchallenge.dto.AccountDto;
import br.com.giovanecarvalho.accountchallenge.dto.EventRequest;
import br.com.giovanecarvalho.accountchallenge.dto.EventResponse;
import br.com.giovanecarvalho.accountchallenge.dto.TransactionType;
import br.com.giovanecarvalho.accountchallenge.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WithdrawStrategy implements TransactionStrategy {

    private final AccountRepository repository;

    public WithdrawStrategy(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public TransactionType type() {
        return TransactionType.WITHDRAW;
    }

    @Override
    public EventResponse execute(EventRequest request) {
        BigDecimal balance = repository.debit(request.origin(), request.amount());
        return new EventResponse(new AccountDto(request.origin(), balance), null);
    }
}
