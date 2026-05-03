package br.com.giovanecarvalho.accountchallenge.exception;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(final String accountId) {
        super("Account not found: " + accountId);
    }
}
