package br.com.giovanecarvalho.accountchallenge.dto;

import java.math.BigDecimal;

public record AccountDto(String id, BigDecimal balance) {
}
