package fr.homingpigeon.conversation.infrastructure;

import fr.homingpigeon.conversation.domain.model.Message;
import fr.homingpigeon.conversation.infrastructure.entities.MessageEntity;
import fr.homingpigeon.conversation.mappers.MessageMapper;
import org.springframework.stereotype.Repository;

@Repository
public class MessageRepository {

    private MessageDAO messageDAO;
    private ConversationDAO conversationDAO;

    public MessageRepository(MessageDAO messageDAO,ConversationDAO conversationDAO) {
        this.messageDAO = messageDAO;
        this.conversationDAO = conversationDAO;
    }

    public void create(Message message) {
        MessageEntity entityToSave = MessageMapper.toEntity(message);
        entityToSave.setConversation(conversationDAO.getOne(message.getConversation_id()));
        entityToSave.setSender(conversationDAO.getOne(message.getConversation_id()).getMembers().stream().filter(x->x.getUsername().equals(message.getSender())).findFirst().get());
        entityToSave.setRecipient(conversationDAO.getOne(message.getConversation_id()).getMembers().stream().filter(x->x.getUsername().equals(message.getRecipient())).findFirst().get());
        messageDAO.save(entityToSave);
    }

    public boolean SenderInConversation(Message message) {
        return conversationDAO.getOne(message.getConversation_id()).getMembers().stream().filter(x->x.getUsername().equals(message.getSender())).findAny().isPresent();
    }

    public boolean RecipientInConversation(Message message) {
        return conversationDAO.getOne(message.getConversation_id()).getMembers().stream().filter(x->x.getUsername().equals(message.getRecipient())).findAny().isPresent();
    }
}
