package br.com.giovanecarvalho.accountchallenge.service;

import br.com.giovanecarvalho.accountchallenge.dto.EventRequest;
import br.com.giovanecarvalho.accountchallenge.dto.EventResponse;
import br.com.giovanecarvalho.accountchallenge.dto.TransactionType;
import br.com.giovanecarvalho.accountchallenge.exception.AccountNotFoundException;
import br.com.giovanecarvalho.accountchallenge.repository.AccountRepository;
import br.com.giovanecarvalho.accountchallenge.service.strategy.TransactionStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final AccountRepository repository;
    private final Map<TransactionType, TransactionStrategy> strategies;

    public EventService(final AccountRepository repository, final List<TransactionStrategy> strategyList) {
        this.repository = repository;
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(TransactionStrategy::getTransactionType, s -> s));
    }

    public void reset() {
        repository.clear();
    }

    public Integer getBalance(final String accountId) {
        return repository.findBalanceById(accountId)
                .map(Number::intValue)
                .orElseThrow(() -> new AccountNotFoundException(accountId));
    }

    public EventResponse processEvent(final EventRequest request) {
        TransactionType type = parseTransactionType(request.type());

        TransactionStrategy strategy = strategies.get(type);

        if (strategy == null) {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        return strategy.execute(request);
    }

    private TransactionType parseTransactionType(final String type) {
        if (type == null) {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        try {
            return TransactionType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Invalid transaction type", exception);
        }
    }
}
