package com.alinazim.ms.ib.accounts.service;

import com.alinazim.ms.ib.accounts.dao.repository.AccountRepository;
import com.alinazim.ms.ib.accounts.model.request.AccountRequest;
import com.alinazim.ms.ib.accounts.model.response.AccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.alinazim.ms.ib.accounts.mapper.AccountMapper.ACCOUNT_MAPPER;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public List<AccountResponse> getAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(ACCOUNT_MAPPER::buildAccountResponse)
                .toList();
    }

    public void createAccount(AccountRequest accountRequest) {
        accountRepository.save(
                ACCOUNT_MAPPER.buildAccountEntity(accountRequest)
        );
    }


    public void updateAccount(String accountNumber, Double balance) {
        var accountEntity = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(
                        () -> new RuntimeException("Account not found with accountNumber: " + accountNumber)
                );

        accountEntity.setBalance(balance);
        accountRepository.save(accountEntity);
    }
}
