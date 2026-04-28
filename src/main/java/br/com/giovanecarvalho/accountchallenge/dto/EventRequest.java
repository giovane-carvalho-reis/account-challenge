package br.com.giovanecarvalho.accountchallenge.dto;

public record EventRequest(String type, String origin, String destination, int amount) {
}
