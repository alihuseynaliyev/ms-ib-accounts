package com.alinazim.ms.ib.accounts.mapper;

import com.alinazim.ms.ib.accounts.dao.entity.AccountEntity;
import com.alinazim.ms.ib.accounts.model.request.AccountRequest;
import com.alinazim.ms.ib.accounts.model.response.AccountResponse;

public enum AccountMapper {

    ACCOUNT_MAPPER;

    public AccountResponse buildAccountResponse(AccountEntity accountEntity) {
        return AccountResponse.builder()
                .accountNumber(accountEntity.getAccountNumber())
                .balance(accountEntity.getBalance())
                .build();
    }

    public AccountEntity buildAccountEntity(AccountRequest accountRequest) {
        return AccountEntity.builder()
                .accountNumber(accountRequest.getAccountNumber())
                .balance(accountRequest.getBalance())
                .build();
    }
}
