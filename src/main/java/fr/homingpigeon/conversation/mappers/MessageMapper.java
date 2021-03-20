package fr.homingpigeon.conversation.mappers;

import fr.homingpigeon.account.mappers.AccountMapper;
import fr.homingpigeon.conversation.domain.model.Conversation;
import fr.homingpigeon.conversation.domain.model.Message;
import fr.homingpigeon.conversation.exposition.dto.MessageDTO;
import fr.homingpigeon.conversation.infrastructure.entities.ConversationEntity;
import fr.homingpigeon.conversation.infrastructure.entities.MessageEntity;

import java.util.List;
import java.util.stream.Collectors;

public class MessageMapper {
    public static Message toMessage(MessageEntity messageEntity) {
        return new Message(messageEntity.getMessage_id(),messageEntity.getContent(),
                messageEntity.getRecipient().getUsername(),
                messageEntity.getSender().getUsername(),messageEntity.getDate(),
                messageEntity.getConversation().getConversation_id());
    }

    public static Message toMessage(MessageDTO messageDTO) {
        return new Message(messageDTO.getMessage_id(),messageDTO.getContent(),
                messageDTO.getRecipient(),
                messageDTO.getSender(),messageDTO.getDate(),
                messageDTO.getConversation_id());
    }

    public static MessageEntity toEntity(Message message) {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessage_id(message.getMessage_id());
        messageEntity.setContent(message.getContent());
        //messageEntity.setRecipient();//a remplir dans le repo
        //messageEntity.setSender();//a remplir dans le repo
        messageEntity.setDate(message.getDate());
        //messageEntity.setConversation();//a remplir dans le repo
        return messageEntity;
    }

    public static MessageDTO toDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessage_id(message.getMessage_id());
        messageDTO.setContent(message.getContent());
        messageDTO.setRecipient(message.getRecipient());
        messageDTO.setSender(message.getSender());
        messageDTO.setDate(message.getDate());
        messageDTO.setConversation_id(message.getConversation_id());
        return messageDTO;
    }

    public static List<Message> toMessages(List<MessageEntity> messageEntities) {
        return messageEntities.stream().map(MessageMapper::toMessage).collect(Collectors.toList());
    }

    public static List<MessageEntity> toEntites(List<Message> messages) {
        return messages.stream().map(MessageMapper::toEntity).collect(Collectors.toList());
    }

    public static List<Message> DtosToMessages(List<MessageDTO> messages) {
        return messages.stream().map(MessageMapper::toMessage).collect(Collectors.toList());
    }

    public static List<MessageDTO> toDTOS(List<Message> messages) {
        return messages.stream().map(MessageMapper::toDTO).collect(Collectors.toList());
    }
}
