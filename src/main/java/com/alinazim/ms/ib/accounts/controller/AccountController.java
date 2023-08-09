package com.alinazim.ms.ib.accounts.controller;

import com.alinazim.ms.ib.accounts.model.request.AccountRequest;
import com.alinazim.ms.ib.accounts.model.response.AccountResponse;
import com.alinazim.ms.ib.accounts.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public List<AccountResponse> getAccounts() {
        return accountService.getAccounts();
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public void createAccount(@RequestBody AccountRequest accountRequest) {
        accountService.createAccount(accountRequest);
    }

    @PutMapping("/{accountNumber}")
    @ResponseStatus(NO_CONTENT)
    public void updateAccount(@PathVariable String accountNumber,
                              @RequestParam Double balance) {
        accountService.updateAccount(accountNumber, balance);
    }
}
