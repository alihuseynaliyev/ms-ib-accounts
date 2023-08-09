package service

import com.alinazim.ms.ib.accounts.dao.entity.AccountEntity
import com.alinazim.ms.ib.accounts.dao.repository.AccountRepository
import com.alinazim.ms.ib.accounts.model.request.AccountRequest
import com.alinazim.ms.ib.accounts.model.response.AccountResponse
import com.alinazim.ms.ib.accounts.service.AccountService
import io.github.benas.randombeans.EnhancedRandomBuilder
import io.github.benas.randombeans.api.EnhancedRandom
import spock.lang.Specification

class AccountServiceTest extends Specification {
    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom()
    private AccountService accountService
    private AccountRepository accountRepository

    void setup() {
        accountRepository = Mock()
        accountService = new AccountService(accountRepository)
    }

    def "Test createAccount() success case"() {
        given:
        def accountRequest = random.nextObject(AccountRequest)
        def accountEntity = AccountEntity.builder()
                .accountNumber(accountRequest.accountNumber)
                .balance(accountRequest.balance)
                .build()


        when:
        accountService.createAccount(accountRequest)

        then:
        1 * accountRepository.save(accountEntity)
    }

    def "Test getAccounts() success case"() {
        given:
        def expectedResponses = [random.nextObject(AccountResponse)]
        def expectedEntities = [AccountEntity.builder()
                                        .balance(expectedResponses[0].balance)
                                        .accountNumber(expectedResponses[0].accountNumber)
                                        .build()
        ]

        when:
        def responses = accountService.getAccounts()

        then:
        1 * accountRepository.findAll() >> expectedEntities
        responses == expectedResponses
    }

    def "Test updateAccount() success case"() {
        given:
        def accountNumber = random.nextObject(String)
        def balance = random.nextDouble()
        def accountEntity = AccountEntity.builder()
                .id(12)
                .accountNumber(accountNumber)
                .balance(balance)
                .build()


        when:
        accountService.updateAccount(accountNumber, balance)

        then:
        1 * accountRepository.findByAccountNumber(accountNumber) >> Optional.of(accountEntity)
        1 * accountRepository.save(accountEntity)
    }
}
