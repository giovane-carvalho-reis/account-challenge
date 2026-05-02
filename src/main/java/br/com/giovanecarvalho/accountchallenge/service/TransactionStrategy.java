package br.com.giovanecarvalho.accountchallenge.service;

import br.com.giovanecarvalho.accountchallenge.dto.EventRequest;
import br.com.giovanecarvalho.accountchallenge.dto.EventResponse;
import br.com.giovanecarvalho.accountchallenge.dto.TransactionType;

public interface TransactionStrategy {
    TransactionType type();
    EventResponse execute(EventRequest request);
}