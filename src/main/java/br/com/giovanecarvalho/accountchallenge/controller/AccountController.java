package br.com.giovanecarvalho.accountchallenge.controller;

import br.com.giovanecarvalho.accountchallenge.dto.EventRequest;
import br.com.giovanecarvalho.accountchallenge.dto.EventResponse;
import br.com.giovanecarvalho.accountchallenge.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final AccountService accountService;

    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> reset() {
        accountService.reset();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/balance")
    public ResponseEntity<Integer> getBalance(@RequestParam("account_id") final String accountId) {
        return ResponseEntity.ok(accountService.getBalance(accountId));
    }

    @PostMapping("/event")
    public ResponseEntity<EventResponse> handleEvent(@RequestBody final EventRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.processEvent(request));
    }
}
