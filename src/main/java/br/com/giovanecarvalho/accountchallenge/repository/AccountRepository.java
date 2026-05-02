package br.com.giovanecarvalho.accountchallenge.repository;

import br.com.giovanecarvalho.accountchallenge.exception.AccountNotFoundException;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AccountRepository {

    private final ConcurrentHashMap<String, BigDecimal> store = new ConcurrentHashMap<>();

    public void clear() {
        store.clear();
    }

    public Optional<BigDecimal> findBalanceById(final String id) {
        return Optional.ofNullable(store.get(id));
    }

    public boolean existsById(final String id) {
        return store.containsKey(id);
    }

    public BigDecimal credit(final String id, final BigDecimal amount) {
        validateAmount(amount);

        return store.compute(id, (k, balance) -> {
            if (balance == null) {
                balance = BigDecimal.ZERO;
            }
            return balance.add(amount);
        });
    }

    public BigDecimal debit(final String id, final BigDecimal amount) {
        validateAmount(amount);

        return store.compute(id, (k, balance) -> {
            if (balance == null) {
                throw new AccountNotFoundException();
            }

            if (balance.compareTo(amount) < 0) {
                throw new IllegalStateException("Saldo insuficiente");
            }

            return balance.subtract(amount);
        });
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }
    }
}
