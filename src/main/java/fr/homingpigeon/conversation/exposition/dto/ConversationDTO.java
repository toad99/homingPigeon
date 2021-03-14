package fr.homingpigeon.conversation.exposition.dto;

import fr.homingpigeon.account.exposition.dto.AccountDTO;

import java.util.List;

public class ConversationDTO {
    private String id_conversation;
    private List<AccountDTO> members;
    private List<MessageDTO> messages;

    public ConversationDTO() {}

    public String getId_conversation() {
        return id_conversation;
    }

    public void setId_conversation(String id_conversation) {
        this.id_conversation = id_conversation;
    }

    public List<AccountDTO> getMembers() {
        return members;
    }

    public void setMembers(List<AccountDTO> members) {
        this.members = members;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
}
