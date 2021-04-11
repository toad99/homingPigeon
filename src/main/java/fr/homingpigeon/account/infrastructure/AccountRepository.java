package fr.homingpigeon.account.infrastructure;

import fr.homingpigeon.account.domain.model.Account;
import fr.homingpigeon.account.mappers.AccountMapper;
import fr.homingpigeon.conversation.infrastructure.ConversationDAO;
import fr.homingpigeon.conversation.infrastructure.entities.ConversationEntity;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class AccountRepository {

    private final AccountDAO accountDAO;
    private final ConversationDAO conversationDAO;

    public AccountRepository(AccountDAO accountDAO,
                             ConversationDAO conversationDAO) {
        this.accountDAO = accountDAO;
        this.conversationDAO = conversationDAO;
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

        List<ConversationEntity> conversations;
        if(account.getConversations() == null)
            conversations = Collections.emptyList();
        else
            conversations =
                    account.getConversations().stream().map(x->conversationDAO.getOne(x)).collect(Collectors.toList());

        entityToSave.setFriendships(friendList);
        entityToSave.setFriend_requests(friend_requestList);
        entityToSave.setConversations(conversations);

        AccountEntity createdAccount = accountDAO.save(entityToSave);

        return AccountMapper.toAccount(createdAccount);
    }

    public Account getOne(String username) {
        AccountEntity entity = accountDAO.getOne(username);
        return AccountMapper.toAccount(entity);
    }

    public boolean exists(String username) {
        return accountDAO.existsById(username);
    }
}
