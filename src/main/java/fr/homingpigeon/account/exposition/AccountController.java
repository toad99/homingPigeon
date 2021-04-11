package fr.homingpigeon.account.exposition;


import fr.homingpigeon.account.mappers.AccountMapper;
import fr.homingpigeon.account.domain.AccountService;
import fr.homingpigeon.account.domain.model.Account;
import fr.homingpigeon.account.exposition.dto.AccountDTO;
import fr.homingpigeon.common.UsefullFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.crypto.SecretKey;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final SecretKey secretKey;

    @Autowired
    public AccountController(AccountService accountService, SecretKey secretKey) {
        this.accountService = accountService;
        this.secretKey = secretKey;
    }

    @PostMapping("/signup")
    public ResponseEntity createAccount(@RequestBody AccountDTO accountDTO) throws URISyntaxException {
        accountDTO.setFriend_requests(Collections.emptyList());
        accountDTO.setFriendships(Collections.emptyList());
        accountDTO.setConversations(Collections.emptyList());

        Account tempo = AccountMapper.toAccount(accountDTO);
        Account createdAccount = accountService.createAccount(tempo);
        return ResponseEntity.created(new URI("/account/" + createdAccount.getUsername())).build();
    }

    @GetMapping()
    public AccountDTO getInfo(@RequestHeader("Authorization") String header) {
        return AccountMapper.toDTO(accountService.getAccount(UsefullFunctions.getUsernameFromHeader(header,secretKey)));
    }

    @GetMapping("/conversations")
    public List<String> getConversation(@RequestHeader("Authorization") String header) {
        return accountService.getAccount(UsefullFunctions.getUsernameFromHeader(header,secretKey)).getConversations();
    }

    @GetMapping("/friends")
    public List<String> getFriends(@RequestHeader("Authorization") String header) {
        return accountService.getAccount(UsefullFunctions.getUsernameFromHeader(header,secretKey)).getFriendships();
    }


    @GetMapping("/{friendo}/add")
    public ResponseEntity addFriend(@RequestHeader("Authorization") String header,
                                    @PathVariable("friendo") String friendo) {
        accountService.addFriend(UsefullFunctions.getUsernameFromHeader(header,secretKey), friendo);
        return ResponseEntity.ok().body("Friend request sent");
    }

    @GetMapping("/{friendo}/accept")
    public ResponseEntity acceptRequest(@RequestHeader("Authorization") String header,
                                        @PathVariable("friendo") String friendo) {
        String username = UsefullFunctions.getUsernameFromHeader(header,secretKey);
        accountService.acceptFriend(username, friendo);
        return ResponseEntity.ok().body("Friend request accepted");
    }

    @GetMapping("/{friendo}/refuse")
    public ResponseEntity refuseRequest(@RequestHeader("Authorization") String header,
                                       @PathVariable("friendo") String friendo) {
        String username = UsefullFunctions.getUsernameFromHeader(header,secretKey);
        accountService.refuseRequest(username, friendo);
        return ResponseEntity.ok().body("Friend request refused");
    }

    @GetMapping("/{friendo}/delete")
    public ResponseEntity deleteFriend(@RequestHeader("Authorization") String header,
                                       @PathVariable("friendo") String friendo) {
        String username = UsefullFunctions.getUsernameFromHeader(header,secretKey);
        accountService.deleteFriend(username, friendo);
        return ResponseEntity.ok().body("Friend removed");
    }
}
