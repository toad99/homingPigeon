package fr.homingpigeon.account.exposition;


import fr.homingpigeon.account.mappers.AccountMapper;
import fr.homingpigeon.account.domain.AccountService;
import fr.homingpigeon.account.domain.model.Account;
import fr.homingpigeon.account.exposition.dto.AccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity createAccount(@RequestBody AccountDTO accountDTO) throws URISyntaxException {
        accountDTO.setFriend_requests(Collections.emptyList());
        accountDTO.setFriendships(Collections.emptyList());
        accountDTO.setConversations(Collections.emptyList());

        Account tempo = AccountMapper.toAccount(accountDTO);
        Account createdAccount = accountService.createAccount(tempo);
        return ResponseEntity.created(new URI("/account/" + createdAccount.getUsername())).build();
    }

    @GetMapping
    public ResponseEntity hello() {
        return ResponseEntity.ok().body("coucou");
    }

}
