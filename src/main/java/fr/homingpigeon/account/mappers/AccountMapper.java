package fr.homingpigeon.account.mappers;

import fr.homingpigeon.account.domain.model.Account;
import fr.homingpigeon.account.exposition.dto.AccountDTO;
import fr.homingpigeon.account.infrastructure.entities.AccountEntity;
import fr.homingpigeon.conversation.mappers.ConversationMapper;

import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {

    public static Account toAccount(AccountDTO accountDTO) {
        return new Account(accountDTO.getUsername(),accountDTO.getPassword(),accountDTO.getPublic_key(),
                accountDTO.getConversations(),
                accountDTO.getFriendships(),accountDTO.getFriend_requests());
    }

    public static Account toAccount(AccountEntity accountEntity) {
        return new Account(accountEntity.getUsername(),accountEntity.getPassword(),accountEntity.getPublic_key(),
                ConversationMapper.toConversations(accountEntity.getConversations()),
                accountEntity.getFriendships().stream().map(x->x.getUsername()).collect(Collectors.toList()),
                accountEntity.getFriend_requests().stream().map(x->x.getUsername()).collect(Collectors.toList()));
    }

    public static List<Account> toAccounts(List<AccountEntity> accountEntities) {
        return accountEntities.stream().map(AccountMapper::toAccount).collect(Collectors.toList());
    }

    public static AccountEntity toEntity(Account account) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setUsername(account.getUsername());
        accountEntity.setPassword(account.getPassword());
        accountEntity.setPublic_key(account.getPublic_key());
        accountEntity.setConversations(ConversationMapper.toEntities(account.getConversations()));
        //ATTRIBUER DANS LE REPO LES ACCOUNTS avec un DAO.get()
        //accountEntity.setFriendships();
        //accountEntity.setFriend_requests();
        return accountEntity;
    }

    public static List<AccountEntity> toEntities(List<Account> account) {
        return account.stream().map(AccountMapper::toEntity).collect(Collectors.toList());
    }
}
