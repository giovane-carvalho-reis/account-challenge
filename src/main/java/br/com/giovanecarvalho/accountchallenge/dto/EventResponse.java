package br.com.giovanecarvalho.accountchallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EventResponse(AccountDto origin, AccountDto destination) {
}
