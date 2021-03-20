package fr.homingpigeon.conversation.exposition.dto;

import fr.homingpigeon.account.exposition.dto.AccountDTO;

import java.util.List;
import java.util.Set;

public class ConversationDTO {
    private String id_conversation;
    private Set<String> members;
    private List<MessageDTO> messages;

    public ConversationDTO() {}

    public String getId_conversation() {
        return id_conversation;
    }

    public void setId_conversation(String id_conversation) {
        this.id_conversation = id_conversation;
    }

    public Set<String> getMembers() {
        return members;
    }

    public void setMembers(Set<String> members) {
        this.members = members;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
}
