package fr.homingpigeon.conversation.mappers;

import fr.homingpigeon.account.mappers.AccountMapper;
import fr.homingpigeon.conversation.domain.model.Conversation;
import fr.homingpigeon.conversation.exposition.dto.ConversationDTO;
import fr.homingpigeon.conversation.infrastructure.entities.ConversationEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ConversationMapper {
    public static ConversationEntity toEntity(Conversation conversation) {
        ConversationEntity conversationEntity = new ConversationEntity();
        conversationEntity.setConversation_id(conversationEntity.getConversation_id());
        conversationEntity.setMessages(MessageMapper.toEntites(conversation.getMessages()));
        // conversationEntity.setMembers(); le remplir dans le repo avec DAO
        return conversationEntity;
    }

    public static ConversationDTO toDTO(Conversation conversation) {
        ConversationDTO conversationDTO = new ConversationDTO();
        conversationDTO.setId_conversation(conversationDTO.getId_conversation());
        conversationDTO.setMessages(MessageMapper.toDTOS(conversation.getMessages()));
        conversationDTO.setMembers(conversation.getMembers());
        return conversationDTO;
    }

    public static List<ConversationEntity> toEntities(List<Conversation> conversation){
        return conversation.stream().map(ConversationMapper::toEntity).collect(Collectors.toList());
    }

    public static Conversation toConversation(ConversationEntity conversationEntity){
        return new Conversation(conversationEntity.getConversation_id(),
                conversationEntity.getMembers().stream().map(x->x.getUsername()).collect(Collectors.toSet()),
                MessageMapper.toMessages(conversationEntity.getMessages()));
    }

    public static Conversation toConversation(ConversationDTO conversationDTO){
        return new Conversation(conversationDTO.getId_conversation(),
                conversationDTO.getMembers(),
                MessageMapper.DtosToMessages(conversationDTO.getMessages()));
    }

    public static List<Conversation> toConversations(List<ConversationEntity> conversations ) {
        return conversations.stream().map(ConversationMapper::toConversation).collect(Collectors.toList());
    }
}
