package br.com.giovanecarvalho.accountchallenge.controller;

import br.com.giovanecarvalho.accountchallenge.dto.EventRequest;
import br.com.giovanecarvalho.accountchallenge.dto.EventResponse;
import br.com.giovanecarvalho.accountchallenge.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {

    private final EventService eventService;

    public AccountController(final EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/reset")
    public ResponseEntity<String> reset() {
        eventService.reset();
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/balance")
    public ResponseEntity<Integer> getBalance(@RequestParam("account_id") final String accountId) {
        return ResponseEntity.ok(eventService.getBalance(accountId));
    }

    @PostMapping("/event")
    public ResponseEntity<EventResponse> handleEvent(@RequestBody final EventRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.processEvent(request));
    }
}
