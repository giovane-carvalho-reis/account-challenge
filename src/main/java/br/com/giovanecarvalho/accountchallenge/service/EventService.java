package br.com.giovanecarvalho.accountchallenge.service;

import br.com.giovanecarvalho.accountchallenge.dto.EventRequest;
import br.com.giovanecarvalho.accountchallenge.dto.EventResponse;
import br.com.giovanecarvalho.accountchallenge.dto.TransactionType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final Map<TransactionType, TransactionStrategy> strategies;

    public EventService(final List<TransactionStrategy> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(TransactionStrategy::type, s -> s));
    }

    public EventResponse processEvent(final EventRequest request) {
        TransactionType type = TransactionType.valueOf(request.type().toUpperCase());

        TransactionStrategy strategy = strategies.get(type);

        if (strategy == null) {
            throw new IllegalArgumentException("Tipo inválido");
        }

        return strategy.execute(request);
    }
}
