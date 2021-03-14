package fr.homingpigeon.conversation.mappers;

import fr.homingpigeon.account.mappers.AccountMapper;
import fr.homingpigeon.conversation.domain.model.Conversation;
import fr.homingpigeon.conversation.infrastructure.entities.ConversationEntity;

import java.util.List;
import java.util.stream.Collectors;

public class ConversationMapper {
    public static ConversationEntity toEntity(Conversation conversation) {
        ConversationEntity conversationEntity = new ConversationEntity();
        conversationEntity.setConversation_id(conversationEntity.getConversation_id());
        conversationEntity.setMessages(MessageMapper.toEntites(conversation.getMessages()));
        conversationEntity.setMembers(AccountMapper.toEntities(conversation.getMembers()));
        return conversationEntity;
    }

    public static List<ConversationEntity> toEntities(List<Conversation> conversation){
        return conversation.stream().map(ConversationMapper::toEntity).collect(Collectors.toList());
    }

    public static Conversation toConversation(ConversationEntity conversationEntity){
        return new Conversation(conversationEntity.getConversation_id(),
                AccountMapper.toAccounts(conversationEntity.getMembers()),
                MessageMapper.toMessages(conversationEntity.getMessages()));
    }

    public static List<Conversation> toConversations(List<ConversationEntity> conversations ) {
        return conversations.stream().map(ConversationMapper::toConversation).collect(Collectors.toList());
    }
}
