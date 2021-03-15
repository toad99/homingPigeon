package fr.homingpigeon.account.exposition;


import fr.homingpigeon.account.mappers.AccountMapper;
import fr.homingpigeon.account.domain.AccountService;
import fr.homingpigeon.account.domain.model.Account;
import fr.homingpigeon.account.exposition.dto.AccountDTO;
import fr.homingpigeon.common.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
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

    @PostMapping("/signup")
    public ResponseEntity createAccount(@RequestBody AccountDTO accountDTO) throws URISyntaxException {
        accountDTO.setFriend_requests(Collections.emptyList());
        accountDTO.setFriendships(Collections.emptyList());
        accountDTO.setConversations(Collections.emptyList());

        Account tempo = AccountMapper.toAccount(accountDTO);
        Account createdAccount = accountService.createAccount(tempo);
        return ResponseEntity.created(new URI("/account/" + createdAccount.getUsername())).build();
    }

    public String getUsernameFromHeader(String header) {
        String token = header.replace("Bearer ", "");
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                                    .setSigningKey(JwtConfig.secretKey())
                                    .build()
                                    .parseClaimsJws(token);

        return claimsJws.getBody().getSubject();
    }

    @GetMapping()
    public AccountDTO getInfo(@RequestHeader("Authorization") String header) {
        return AccountMapper.toDTO(accountService.getAccount(getUsernameFromHeader(header)));
    }

    @GetMapping("/add/{friendo}")
    public ResponseEntity addFriend(@RequestHeader("Authorization") String header,@PathVariable("friendo") String friendo) {
        accountService.addFriend(getUsernameFromHeader(header),friendo);
        return ResponseEntity.ok().body("Friend request sent");
    }

    @GetMapping("/accept/{friendo}")
    public ResponseEntity acceptFriend(@RequestHeader("Authorization") String header,
                                 @PathVariable("friendo") String friendo) {
        String token = header.replace("Bearer ", "");
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                                    .setSigningKey(JwtConfig.secretKey())
                                    .build()
                                    .parseClaimsJws(token);
        Claims body = claimsJws.getBody();
        String username = body.getSubject();
        accountService.acceptFriend(username,friendo);
        return ResponseEntity.ok().body("Friend request sent");
    }

}
