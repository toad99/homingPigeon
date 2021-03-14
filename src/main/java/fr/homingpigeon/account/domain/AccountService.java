package fr.homingpigeon.account.domain;

import fr.homingpigeon.common.ValidationError;
import fr.homingpigeon.common.exception.ValidationErrorException;
import fr.homingpigeon.account.domain.model.Account;
import fr.homingpigeon.account.infrastructure.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AccountService {

    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        List<ValidationError> validationErrors = validate(account);
        if(validationErrors.size() != 0)
            throw new ValidationErrorException(validationErrors);
        return accountRepository.create(account);
    }

    private List<ValidationError> validate(Account account) {
        //TODO
        return Collections.emptyList();
    }
}
