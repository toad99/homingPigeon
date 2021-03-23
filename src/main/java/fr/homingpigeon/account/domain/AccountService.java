package fr.homingpigeon.account.domain;

import fr.homingpigeon.common.ValidationError;
import fr.homingpigeon.common.exception.ValidationErrorException;
import fr.homingpigeon.account.domain.model.Account;
import fr.homingpigeon.account.infrastructure.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${variables.passwordMinLength}")
    private Integer passwordMinLength;
    @Value("${variables.passwordMinLength}")
    private Integer passwordMaxLength;
    @Value("${variables.publicKeyMinLength}")
    private Integer publicKeyMinLength;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        List<ValidationError> validationErrors = validateCreate(account);
        if(validationErrors.size() != 0)
            throw new ValidationErrorException(validationErrors);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.create(account);
    }

    private List<ValidationError> validateCreate(Account account) {
        List<ValidationError> validationErrors = new ArrayList<>();

        if(account.getUsername() == null)
            validationErrors.add(new ValidationError("username not specified"));
        else if(account.getUsername().length() == 0)
            validationErrors.add(new ValidationError("username empty"));
        else if(account.getUsername().length() > 32 || account.getUsername().length() < 4)
            validationErrors.add(new ValidationError("username do not contain between 4 and 32 characters"));
        else if(accountRepository.exists(account.getUsername()))
            validationErrors.add(new ValidationError("username already taken"));

        if(account.getPassword() == null)
            validationErrors.add(new ValidationError("password not specified"));
        else if(account.getPassword().length() < passwordMinLength || account.getPassword().length() > passwordMaxLength)
            validationErrors.add(new ValidationError("password must have between " + passwordMinLength + " and " + passwordMaxLength + " characters"));

        if(account.getPublic_key() == null)
            validationErrors.add(new ValidationError("public key not specified"));
        else if(account.getPublic_key().length() < publicKeyMinLength)
            validationErrors.add(new ValidationError("the public key must be at least " + publicKeyMinLength + " " +
                    "bytes long"));

        return validationErrors;
    }

    public Account getAccount(String username) {
        return accountRepository.getOne(username);
    }

    public void addFriend(String username, String friendo) {
        List<ValidationError> validationErrors = validateAddFriend(username, friendo);
        if(validationErrors.size() != 0)
            throw new ValidationErrorException(validationErrors);

        Account friend = accountRepository.getOne(friendo);
        friend.addFriend_request(username);
        accountRepository.create(friend);
    }

    private List<ValidationError> validateAddFriend(String username, String friendo) {
        List<ValidationError> validationErrors = new ArrayList<>();
        if(username.equals(friendo))
            validationErrors.add(new ValidationError("You can't be friend with yourself !"));

        if(accountRepository.getOne(friendo)
                            .getFriend_requests()
                            .stream()
                            .filter(x -> x.equals(username)).findAny().isPresent())
            validationErrors.add(new ValidationError("Request already sent"));
        if(accountRepository.getOne(username)
                            .getFriendships()
                            .stream()
                            .filter(x -> x.equals(friendo)).findAny().isPresent())
            validationErrors.add(new ValidationError("Already friends"));
        return validationErrors;
    }

    public void acceptFriend(String username, String friendo) {
        List<ValidationError> validationErrors = validateAcceptFriend(username, friendo);
        if(validationErrors.size() != 0)
            throw new ValidationErrorException(validationErrors);

        Account user = accountRepository.getOne(username);
        user.deleteFriend_request(friendo);
        user.addFriendship(friendo);
        accountRepository.create(user);
    }

    private List<ValidationError> validateAcceptFriend(String username, String friendo) {
        List<ValidationError> validationErrors = new ArrayList<>();
        if(username.equals(friendo))
            validationErrors.add(new ValidationError("You can't be friend with yourself !"));

        if(accountRepository.getOne(username)
                            .getFriend_requests()
                            .stream()
                            .filter(x -> x.equals(friendo)).findAny().isEmpty())
            validationErrors.add(new ValidationError(friendo + " never wanted to be your friend and probably never " +
                    "will"));
        if(accountRepository.getOne(username)
                            .getFriendships()
                            .stream()
                            .filter(x -> x.equals(friendo)).findAny().isPresent())
            validationErrors.add(new ValidationError("Already friends"));
        return validationErrors;
    }

    public void refuseRequest(String username, String friendo) {
        List<ValidationError> validationErrors = validateRefuseRequest(username,friendo);
        if(validationErrors.size() != 0)
            throw new ValidationErrorException(validationErrors);
        Account account = accountRepository.getOne(username);
        account.getFriend_requests().remove(friendo);
        accountRepository.create(account);
    }

    private List<ValidationError> validateRefuseRequest(String username, String friendo) {
        List<ValidationError> validationErrors = new ArrayList<>();
        if(username.equals(friendo))
            validationErrors.add(new ValidationError("You can't be friend with yourself in the first place!"));

        if(accountRepository.getOne(username)
                            .getFriend_requests()
                            .stream()
                            .filter(x -> x.equals(friendo)).findAny().isEmpty())
            validationErrors.add(new ValidationError(friendo + " never wanted to be your friend and probably never " +
                    "will"));
        if(accountRepository.getOne(username)
                            .getFriendships()
                            .stream()
                            .filter(x -> x.equals(friendo)).findAny().isPresent())
            validationErrors.add(new ValidationError("Too late ! you are already friends"));
        return validationErrors;
    }

    public void deleteFriend(String username, String friendo) {
        List<ValidationError> validationErrors = validateDeleteFriend(username,friendo);
        if(validationErrors.size() != 0)
            throw new ValidationErrorException(validationErrors);
        Account account = accountRepository.getOne(username);
        account.getFriendships().remove(friendo);
        accountRepository.create(account);
    }

    private List<ValidationError> validateDeleteFriend(String username, String friendo) {
        List<ValidationError> validationErrors = new ArrayList<>();
        if(username.equals(friendo))
            validationErrors.add(new ValidationError("You can't be friend with yourself in the first place!"));
        if(accountRepository.getOne(username)
                            .getFriendships()
                            .stream()
                            .filter(x -> x.equals(friendo)).findAny().isEmpty())
            validationErrors.add(new ValidationError(friendo + "never was your friend"));
        return validationErrors;
    }
}
