package fr.homingpigeon.conversation.infrastructure;

import fr.homingpigeon.account.infrastructure.AccountDAO;
import fr.homingpigeon.account.infrastructure.AccountEntity;
import fr.homingpigeon.conversation.domain.model.Conversation;
import fr.homingpigeon.conversation.infrastructure.entities.ConversationEntity;
import fr.homingpigeon.conversation.mappers.ConversationMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class ConversationRepository {
    private ConversationDAO conversationDAO;
    private AccountDAO accountDAO;

    public ConversationRepository(ConversationDAO conversationDAO, AccountDAO accountDAO) {
        this.conversationDAO = conversationDAO;
        this.accountDAO = accountDAO;
    }

    public Conversation create(Conversation conversation) {
        ConversationEntity entityToSave = ConversationMapper.toEntity(conversation);
        List<AccountEntity> members;
        if(conversation.getMembers() == null)
            members = Collections.emptyList();
        else
            members =
                    conversation.getMembers().stream().map(x->accountDAO.getOne(x)).collect(Collectors.toList());

        entityToSave.setMembers(members);
        return ConversationMapper.toConversation(conversationDAO.save(entityToSave));
    }

    public Conversation getOne(String id) {
        return ConversationMapper.toConversation(conversationDAO.getOne(id));
    }

    public boolean exists(Set<String> usernames) {
        List<ConversationEntity> conversationEntities = conversationDAO.findAll();
        return conversationEntities.stream()
                             .map(x->x.getMembers().stream().map(accountEntity->accountEntity.getUsername()).collect(Collectors.toSet()))
                             .filter(x-> x.equals(usernames))
                             .findAny().isPresent();

    }
}
