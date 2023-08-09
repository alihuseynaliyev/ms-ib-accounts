package controller

import com.alinazim.ms.ib.accounts.controller.AccountController
import com.alinazim.ms.ib.accounts.model.request.AccountRequest
import com.alinazim.ms.ib.accounts.model.response.AccountResponse
import com.alinazim.ms.ib.accounts.service.AccountService
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification


import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AccountControllerTest extends Specification {

    private AccountController accountController
    private AccountService accountService
    private MockMvc mockMvc

    void setup() {
        accountService = Mock()
        accountController = new AccountController(accountService)
        mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .build()
    }

    def "Test getAccounts() success case"() {
        given:
        def url = "/api/accounts"
        def expectedResult = '''
                [{
                  "accountNumber": "12345",
                  "balance": 1515.0
                }
                ]
        '''

        def response = [AccountResponse.builder()
                                .accountNumber("12345")
                                .balance(1515)
                                .build()]

        when:
        def result = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn()

        then:
        1 * accountService.getAccounts() >> response
        JSONAssert.assertEquals(expectedResult, result.response.contentAsString, false)
    }

    def "Test createAccount() success case"() {
        given:
        def url = "/api/accounts"
        def request = AccountRequest.builder()
                .accountNumber("1010")
                .balance(2020)
                .build()

        def content = '''
            {
                "accountNumber" : "1010",
                "balance": 2020
            }
        '''

        when:
        mockMvc.perform(post(url)
                .contentType(APPLICATION_JSON)
                .content(content))
                .andExpect(status().isCreated())

        then:
        1 * accountService.createAccount(request)
    }

    def "Test updateAccount() success case"() {
        given:
        def accountNumber = "1212"
        def balance = 120
        def url = "/api/accounts/${accountNumber}"

        when:
        mockMvc.perform(put(url).param("balance", "${balance}"))
                .andExpect(status().isNoContent())

        then:
        1 * accountService.updateAccount(accountNumber, balance)
    }
}
