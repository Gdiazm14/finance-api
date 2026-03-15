package org.gdiazm.finance.app.finance.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gdiazm.finance.app.finance.account.dto.AccountRequest;
import org.gdiazm.finance.app.finance.account.dto.AccountResponse;
import org.gdiazm.finance.app.finance.account.dto.AccountUpdateRequest;
import org.gdiazm.finance.app.finance.account.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Valid @RequestBody AccountRequest request){
        return accountService.createAccount(request);
    }

    @GetMapping
    public List<AccountResponse> getAccounts(@RequestParam(required = false) Boolean active){
        return accountService.getAccounts(active);
    }

    @GetMapping("/{id}")
    public AccountResponse getAccountById(@PathVariable UUID id){
        return accountService.getAccountById(id);
    }
    @PatchMapping("/{id}")
    public AccountResponse updateAccount(@PathVariable UUID id,@Valid @RequestBody AccountUpdateRequest request){
        return accountService.updateAccount(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable UUID id){
        accountService.deleteAccountById(id);
    }

}
