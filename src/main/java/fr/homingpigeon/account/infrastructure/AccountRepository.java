package fr.homingpigeon.account.infrastructure;

import fr.homingpigeon.account.domain.model.Account;
import fr.homingpigeon.account.mappers.AccountMapper;
import fr.homingpigeon.account.infrastructure.entities.AccountEntity;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class AccountRepository {

    private final AccountDAO accountDAO;

    public AccountRepository(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account create(Account account) {
        AccountEntity entityToSave = AccountMapper.toEntity(account);
        List<AccountEntity> friendList;
        if(account.getFriendships() == null)
            friendList = Collections.emptyList();
        else
            friendList =
                    account.getFriendships().stream().map(x->accountDAO.getOne(x)).collect(Collectors.toList());

        List<AccountEntity> friend_requestList;
        if(account.getFriend_requests() == null)
            friend_requestList = Collections.emptyList();
        else
            friend_requestList = account.getFriend_requests().stream().map(x->accountDAO.getOne(x)).collect(Collectors.toList());

        entityToSave.setFriendships(friendList);
        entityToSave.setFriend_requests(friend_requestList);
        AccountEntity createdAccount = accountDAO.save(entityToSave);
        return AccountMapper.toAccount(createdAccount);
    }

    public Account getOne(String username) {
        AccountEntity entity = accountDAO.getOne(username);
        return AccountMapper.toAccount(entity);
    }
}
