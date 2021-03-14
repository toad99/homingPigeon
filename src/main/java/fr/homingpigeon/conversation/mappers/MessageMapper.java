package fr.homingpigeon.conversation.mappers;

import fr.homingpigeon.account.mappers.AccountMapper;
import fr.homingpigeon.conversation.domain.model.Message;
import fr.homingpigeon.conversation.infrastructure.entities.ConversationEntity;
import fr.homingpigeon.conversation.infrastructure.entities.MessageEntity;

import java.util.List;
import java.util.stream.Collectors;

public class MessageMapper {
    public static Message toMessage(MessageEntity messageEntity) {
        return new Message(messageEntity.getMessage_id(),messageEntity.getContent(),
                AccountMapper.toAccount(messageEntity.getRecipient()),
                AccountMapper.toAccount(messageEntity.getSender()),messageEntity.getDate(),
                ConversationMapper.toConversation(messageEntity.getConversation()));
    }

    public static List<Message> toMessages(List<MessageEntity> messageEntities) {
        return messageEntities.stream().map(MessageMapper::toMessage).collect(Collectors.toList());
    }

    public static MessageEntity toEntity(Message message) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessage_id(message.getId_conversation());
        messageEntity.setContent(message.getContent());
        messageEntity.setRecipient(AccountMapper.toEntity(message.getRecipient()));
        messageEntity.setSender(AccountMapper.toEntity(message.getSender()));
        messageEntity.setDate(message.getDate());
        messageEntity.setConversation(ConversationMapper.toEntity(message.getConversation()));
        return messageEntity;
    }

    public static List<MessageEntity> toEntites(List<Message> messages) {
        return messages.stream().map(MessageMapper::toEntity).collect(Collectors.toList());
    }
}
