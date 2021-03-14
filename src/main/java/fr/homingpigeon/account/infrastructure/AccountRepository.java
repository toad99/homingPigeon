package fr.homingpigeon.account.infrastructure;

import fr.homingpigeon.account.domain.model.Account;
import fr.homingpigeon.account.mappers.AccountMapper;
import fr.homingpigeon.account.infrastructure.entities.AccountEntity;
import org.springframework.stereotype.Repository;


@Repository
public class AccountRepository {

    private final AccountDAO accountDAO;

    public AccountRepository(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account create(Account account) {
        AccountEntity entityToSave = AccountMapper.toEntity(account);
        AccountEntity createdAccount = accountDAO.save(entityToSave);
        return AccountMapper.toAccount(createdAccount);
    }

    public Account findOne(String username) {
        AccountEntity entity = accountDAO.getOne(username);
        return AccountMapper.toAccount(entity);
    }
}
